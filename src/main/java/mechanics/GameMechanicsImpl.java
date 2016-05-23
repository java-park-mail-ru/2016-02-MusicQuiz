package mechanics;

import base.GameMechanics;
import base.GameUser;
import base.WebSocketService;
import main.AccountService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rest.Helper;
import rest.UserAnswer;

import java.time.Clock;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 19.05.16.
 */
public class GameMechanicsImpl implements GameMechanics {

    private static final long STEP_TIME = 100;
    @SuppressWarnings("ConstantConditions")

    private static final long GAME_TIME = TimeUnit.SECONDS.toMillis(15);


    @NotNull
    private WebSocketService webSocketService;

    @NotNull
    private Map<Long, GameSession> nameToGame = new HashMap<>();

    @NotNull
    private Set<GameSession> allSessions = new HashSet<>();

    @Nullable
    private volatile Long waiter;

    @NotNull
    private Clock clock = Clock.systemDefaultZone();

    @NotNull
    private final Queue<Runnable> tasks = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(@NotNull WebSocketService webSocketService, AccountService accountService) {
        this.webSocketService = webSocketService;
    }

    @Override
    public void addUser(@NotNull Long user) {
        tasks.add(() -> addUserInternal(user));
    }

    private void addUserInternal(@NotNull Long user) {
        if (waiter != null) {
            //noinspection ConstantConditions
            startGame(user, waiter);
            waiter = null;
        } else {
            waiter = user;
        }
    }

    @Override
    public void removeUser(long user) {
        tasks.add(() -> removeUserInternal(user));
    }

    private void removeUserInternal(long user) {
        nameToGame.remove(user);
    }

    @Override
    public void choice(long user_id, UserAnswer ans) {
        tasks.add(()->user_choice(user_id, ans));
    }

    private void user_choice(long user_id, UserAnswer ans) {

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
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
        for (GameSession session : allSessions) {
            if (session.getSessionTime() > GAME_TIME) {
                boolean firstWin = session.isFirstWin();
                webSocketService.notifyGameOver(session.getFirst(), session.isFirstWin(),
                        session.getFirst().getMyScore(), session.getSecond().getMyScore());
                webSocketService.notifyGameOver(session.getSecond(), !session.isFirstWin(),
                        session.getSecond().getMyScore(), session.getFirst().getMyScore());
            }
        }
    }

    private void startGame(@NotNull Long first, @NotNull Long second) {
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);
        webSocketService.notifyStartGame(gameSession.getSelf(first), 1/*gameSession.id*/,
                gameSession.getTrackId(gameSession.getSelf(first)),gameSession.getAnswers(gameSession.getSelf(first)),
                30/*time*/);
        webSocketService.notifyStartGame(gameSession.getSelf(second), 1/*gameSession.id*/,
                gameSession.getTrackId(gameSession.getSelf(second)),gameSession.getAnswers(gameSession.getSelf(second)),
                30/*time*/);
    }

    public void timeout(long user_id) {
        tasks.add(()->timeoutInternal(user_id));
    }

    private void timeoutInternal(long user_id) {
        //
    }


    //здесь есть начало и завершение игры
    //дописать еще действия на ответы игрока на вопросы
    //также понять условия когда игра заканчивается
    //действия при завершении игры описаны в gmStep
}

