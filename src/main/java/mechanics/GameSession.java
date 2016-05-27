package mechanics;

import base.GameUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rest.GameQuestions;
import rest.UserAnswer;

import java.time.Clock;
import java.util.HashMap;
import java.util.HashSet;
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
    private long sessionId;

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    @NotNull
    private Map<Long, GameUser> users = new HashMap<>();

    private GameQuestions songs;

    public GameSession(@NotNull Long user1, @NotNull Long user2) {
        startTime = Clock.systemDefaultZone().millis();
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setOpponentId(user2);

        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setOpponentId(user1);

        System.out.println("USER:::" + user1 + " gameUser:::" + gameUser1.getMyId());
        System.out.println("USER:::" + user2 + " gameUser:::" + gameUser2.getMyId());
        users.put(user1, gameUser1);
        users.put(user2, gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;
        this.sessionId = ID_GENERATOR.getAndIncrement();

        //songs =  new GameQuestions();
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

    public long getTrackId(Long id) {
        GameUser user = getSelf(id);
        //long res = songs.getIdQuestion(user.getNumQuestion());
        user.incrementyQuestion();
        return 1;
        //return res;
    }

    public Set<String> getAnswers(Long id) {
        GameUser user = getSelf(id);
        Set<String> ans = new HashSet<>();
        ans.add("HI");
        ans.add("IT");
        ans.add("IS");
        ans.add("WORKING");
        return ans;
        //return songs.getAnswers(user.getNumQuestion());
    }

    public String getRightAnswer(Long id) {
        GameUser user = getSelf(id);
        return songs.getRightAnswer(user.getNumQuestion());
    }

    @NotNull
    public GameUser getSecond() {
        return second;
    }

    public boolean isFirstWin() {
        return first.getMyScore() > second.getMyScore();
    }

    public void isRightAns(Long userId, UserAnswer answer) {
        if (getRightAnswer(userId).equals(answer.getAns())) {
            getSelf(userId).incrementMyScore();
            getEnemy(userId).incrementEnemyScore();
        }
    }

    public long getSessionId() {
        return sessionId;
    }
}
