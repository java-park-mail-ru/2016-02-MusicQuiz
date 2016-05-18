package base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by seven-teen on 17.05.16.
 */
public class GameUser {
    @NotNull
    private final String myName;
    @Nullable
    private String opponentName;
    private int myScore = 0;
    private int opponentScore = 0;

    public GameUser(@NotNull String myName) {
        this.myName = myName;
    }

    @NotNull
    public String getMyName() {
        return myName;
    }
    @Nullable
    public String getOpponentName() {
        return opponentName;
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

    public void setOpponentName(@NotNull String opponentName) {
        this.opponentName = opponentName;
    }

}
