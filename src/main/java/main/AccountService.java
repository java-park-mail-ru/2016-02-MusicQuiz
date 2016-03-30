package main;

import org.jetbrains.annotations.Nullable;
import rest.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IlyaRogov on 29.02.16.
 */

public interface AccountService {

    boolean addUser(UserProfile userProfile);

    UserProfile getUser(Long id);

    @Nullable
    UserProfile getUserBySession(String SessionID);

    @Nullable
    UserProfile getUserByLogin(String login);

    void deleteUser(Long id);

    void updateUser(UserProfile user, UserProfile changedUser);

    void logIn(String SessionID, UserProfile user);

    void logOut(String SessionID);
}
