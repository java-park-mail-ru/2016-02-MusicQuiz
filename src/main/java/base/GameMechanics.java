package base;

import org.jetbrains.annotations.NotNull;

/**
 * Created by seven-teen on 18.05.16.
 */

public interface GameMechanics {
    public void addUser(@NotNull String user);

    public void incrementScore(@NotNull String userName);

    public void run();

}
