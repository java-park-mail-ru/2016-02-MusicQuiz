package base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by seven-teen on 17.05.16.
 */
public class GameUser {
    @NotNull
    private final Long myId;
    @Nullable
    private Long opponentId;
    private int myScore = 0;
    private int opponentScore = 0;

    public GameUser(@NotNull Long myName) {
        this.myId = myName;
    }

    @NotNull
    public Long getMyId() {
        return myId;
    }
    @Nullable
    public Long getOpponentId() {
        return opponentId;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void incrementMyScore() {
        myScore++;
    }

    public void incrementEnemyScore() {
        opponentScore++;
    }

    public void setOpponentId(@NotNull Long opponentId) {
        this.opponentId = opponentId;
    }

}
