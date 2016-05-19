package base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by seven-teen on 18.05.16.
 */

public interface GameMechanics {
    public void addUser(@NotNull Long user);

    public void incrementScore(@NotNull Long user);

    public void removeUser(long user);

    public void run();

}
