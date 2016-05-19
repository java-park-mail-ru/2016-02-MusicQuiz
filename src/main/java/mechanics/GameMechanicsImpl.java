package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import main.AccountService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rest.Helper;

import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 19.05.16.
 */
public class GameMechanicsImpl implements GameMechanics{

        private static final long STEP_TIME = 100;
        @SuppressWarnings("ConstantConditions")

        private static final long GAME_TIME = TimeUnit.SECONDS.toMillis(15);


        @NotNull
        private WebSocketService webSocketService;

        @NotNull
        private Map<String, GameSession> nameToGame = new HashMap<>();

        @NotNull
        private Set<GameSession> allSessions = new HashSet<>();

        @Nullable
        private volatile String waiter;

        @NotNull
        private Clock clock = Clock.systemDefaultZone();

        @NotNull
        private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

        public GameMechanicsImpl(@NotNull WebSocketService webSocketService, AccountService accountService) {
            this.webSocketService = webSocketService;
        }

        @Override
        public void addUser(@NotNull Long user) {
            tasks.add(()->addUserInternal(user));
        }

        private void addUserInternal(@NotNull Long user) {
            if (waiter != null) {
                //noinspection ConstantConditions
                starGame(user, waiter);
                waiter = null;
            } else {
                waiter = user;
            }
        }

        @Override
        public void incrementScore(@NotNull Long userName) {
            tasks.add(() -> incrementScoreInternal(userName));
        }

        private void incrementScoreInternal(Long userName) {
            GameSession myGameSession = nameToGame.get(userName);
            GameUser myUser = myGameSession.getSelf(userName);
            myUser.incrementMyScore();
            GameUser enemyUser = myGameSession.getEnemy(userName);
            enemyUser.incrementEnemyScore();
            webSocketService.notifyMyNewScore(myUser);
            webSocketService.notifyEnemyNewScore(enemyUser);
        }

        @Override
        public void run() {
            long lastFrameMillis = STEP_TIME;
            //noinspection InfiniteLoopStatement
            while (true) {
                final long before = clock.millis();
                gmStep(lastFrameMillis);
                final long after = clock.millis();
                Helper.sleep(STEP_TIME - (after - before));

                final long afterSleep = clock.millis();
                lastFrameMillis = afterSleep - before;
            }
        }

        private void gmStep(long frameTime) {
            while (!tasks.isEmpty()) {
                final Runnable nextTask = tasks.poll();
                if (nextTask != null) {
                    try {
                        nextTask.run();
                    } catch(RuntimeException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            for (GameSession session : allSessions) {
                if (session.getSessionTime() > GAME_TIME) {
                    boolean firstWin = session.isFirstWin();
                    webSocketService.notifyGameOver(session.getFirst(), firstWin);
                    webSocketService.notifyGameOver(session.getSecond(), !firstWin);
                }
            }
        }


        private void starGame(@NotNull Long first, @NotNull Long second) {
            GameSession gameSession = new GameSession(first, second);
            allSessions.add(gameSession);
            nameToGame.put(first, gameSession);
            nameToGame.put(second, gameSession);

            webSocketService.notifyStartGame(gameSession.getSelf(first));
            webSocketService.notifyStartGame(gameSession.getSelf(second));
        }
    }

