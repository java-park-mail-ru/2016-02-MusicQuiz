package base;

import org.jetbrains.annotations.NotNull;
import rest.UserAnswer;

/**
 * Created by seven-teen on 18.05.16.
 */

public interface GameMechanics {
    public void addUser(@NotNull Long user);

    public void incrementScore(@NotNull Long user);

    public void choice(long user_id, UserAnswer ans);

    public void removeUser(long user);

    public void run();

}
