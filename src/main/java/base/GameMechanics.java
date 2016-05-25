package base;

import org.jetbrains.annotations.NotNull;
import rest.UserAnswer;

/**
 * Created by seven-teen on 18.05.16.
 */

public interface GameMechanics {
     void addUser(@NotNull Long user);

     void choice(Long id, UserAnswer ans);

     void removeUser(long user);

     void run();

     void timeout(Long id);

}
