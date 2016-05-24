package rest;

/**
 * Created by user on 19.05.16.
 */
public class UserAnswer {
    private final String ans;

    private final long gamesessionId;

    public UserAnswer(long gamesessionId, String ans)  {
        this.gamesessionId = gamesessionId;
        this.ans = ans;
    }
}
