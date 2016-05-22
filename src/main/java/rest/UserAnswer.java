package rest;

/**
 * Created by user on 19.05.16.
 */
public class UserAnswer {
    private final String ans;

    private final long id_gamesession;

    public UserAnswer(long id_gamesession, String ans)  {
        this.id_gamesession = id_gamesession;
        this.ans = ans;
    }
}
