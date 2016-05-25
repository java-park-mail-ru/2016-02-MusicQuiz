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

/**
 * Created by user on 19.05.16.
 */
public class GameMechanicsImpl implements GameMechanics {

    private static final long STEP_TIME = 100;
    @SuppressWarnings("ConstantConditions")

    private static final long SESSION_TIME = 30L;

    @NotNull
    private WebSocketService webSocketService;

    @NotNull
    private final AccountService accountService;


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

    public GameMechanicsImpl(@NotNull WebSocketService webSocketService, @NotNull AccountService accountService) {
        this.webSocketService = webSocketService;
        this.accountService = accountService;
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
    public void choice(Long userId, UserAnswer ans) {
        tasks.add(() -> userChoice(userId, ans));
    }

    private void userChoice(Long userId, UserAnswer ans) {

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

    }

    private void startGame(@NotNull Long first, @NotNull Long second) {
        GameSession gameSession = new GameSession(first, second);
        allSessions.add(gameSession);
        GameUser firstUser = gameSession.getSelf(first);
        GameUser secondUser = gameSession.getSelf(second);
        if (firstUser == null || secondUser == null) {
            System.out.println("User not found");
            return;
        }
        nameToGame.put(first, gameSession);
        nameToGame.put(second, gameSession);
        webSocketService.notifyStartGame(firstUser, gameSession.getSessionId(), gameSession.getTrackId(firstUser), gameSession.getAnswers(firstUser), SESSION_TIME);
        webSocketService.notifyStartGame(secondUser, gameSession.getSessionId(), gameSession.getTrackId(secondUser), gameSession.getAnswers(secondUser), SESSION_TIME);
    }

    @Override
    public void timeout(Long userId) {
        GameSession session = nameToGame.get(userId);
        tasks.add(() -> timeoutInternal(session));
    }

    private void timeoutInternal(GameSession session) {
        boolean firstWin = session.isFirstWin();
        webSocketService.notifyGameOver(session.getFirst(), firstWin, session.getFirst().getMyScore(), session.getSecond().getMyScore());
        webSocketService.notifyGameOver(session.getSecond(), !firstWin, session.getSecond().getMyScore(), session.getFirst().getMyScore());
        accountService.updatePoints(session.getFirst().getMyId(), session.getFirst().getMyScore());
        accountService.updatePoints(session.getSecond().getMyId(), session.getSecond().getMyScore());
        allSessions.remove(session);
    }
}

