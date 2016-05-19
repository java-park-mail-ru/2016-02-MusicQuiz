package rest;

/**
 * Created by user on 19.05.16.
 */
public class UserAnswer {
    private final String ans;

    private final int question;

    public UserAnswer(int question, String ans)  {
        this.ans = ans;
        this.question = question;
    }
}
