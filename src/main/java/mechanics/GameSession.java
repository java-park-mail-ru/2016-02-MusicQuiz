package mechanics;

import base.GameUser;
import database.MusicDAO;
import database.MusicDataSet;
import database.UsersDataSet;
import main.AccountService;
import main.AccountServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rest.GameQuestions;

import javax.inject.Inject;
import java.time.Clock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private Map<String, GameUser> users = new HashMap<>();

    private GameQuestions songs;

    public GameSession(@NotNull String user1, @NotNull String user2) {
        startTime = Clock.systemDefaultZone().millis();
        GameUser gameUser1 = new GameUser(user1);
        gameUser1.setOpponentName(user2);

        GameUser gameUser2 = new GameUser(user2);
        gameUser2.setOpponentName(user1);

        users.put(user1, gameUser1);
        users.put(user2, gameUser2);

        this.first = gameUser1;
        this.second = gameUser2;

        songs =  new GameQuestions();
    }

    @Nullable
    public GameUser getEnemy(@NotNull Long user) {
        @SuppressWarnings("ConstantConditions")
        String enemyName = users.containsKey(user) ? users.get(user).getOpponentName() : null;
        return enemyName == null ? null : users.get(enemyName);
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

    @NotNull
    public GameUser getSecond() {
        return second;
    }

    public boolean isFirstWin() {
        return first.getMyScore() > second.getMyScore();
    }
}
