package mechanics;

import base.GameUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rest.GameQuestions;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by user on 21.04.16.
 */
public class GameSession {
    private final long startTime;
    @NotNull
    private final GameUser first;
    @NotNull
    private final GameUser second;



    @NotNull
    private long sessionId;

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    @NotNull
    private Map<Long, GameUser> users = new HashMap<>();

    private GameQuestions songs;

    public GameSession(@NotNull Long user1, @NotNull Long user2) {
        startTime = Clock.systemDefaultZone().millis();
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setOpponentId(user2);

        GameUser gameUser2 = new GameUser(user1);
        gameUser2.setOpponentId(user2);

        users.put(user1, gameUser1);
        users.put(user2, gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;
        this.sessionId = ID_GENERATOR.getAndIncrement();

        songs =  new GameQuestions();
    }

    @Nullable
    public GameUser getEnemy(@NotNull Long user) {
        @SuppressWarnings("ConstantConditions")
        final long enemyId = users.containsKey(user) ? users.get(user).getOpponentId() : -1;
        return enemyId == -1 ? null : users.get(enemyId);
    }

    @Nullable
    public GameUser getSelf(Long user) {
        return users.get(user);
    }

    public long getSessionTime(){
        return Clock.systemDefaultZone().millis() - startTime;
    }

    @NotNull
    public GameUser getFirst() {
        return first;
    }

    public long getTrackId(GameUser user) {
        return songs.getIdQuestion(user.getNumQuestion());
    }

    public Set<String> getAnswers(GameUser user) {
        return songs.getAnswers(user.getNumQuestion());
    }

    @NotNull
    public GameUser getSecond() {
        return second;
    }

    public boolean isFirstWin() {
        return first.getMyScore() > second.getMyScore();
    }

    @NotNull
    public long getSessionId() {
        return sessionId;
    }
}
