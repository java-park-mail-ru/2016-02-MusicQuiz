package main;

import org.jetbrains.annotations.Nullable;
import rest.UserProfile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IlyaRogov on 29.02.16.
 */

public interface AccountService {

    Collection<UserProfile> getAllUsers();

    boolean addUser(UserProfile userProfile);

    UserProfile getUser(Long id);

    @Nullable
    UserProfile getUserBySession(String SessionID);

    @Nullable
    UserProfile getUserByLogin(String login);

    void deleteUser(Long id);

    void print_sessions();

    void updateUser(UserProfile user, UserProfile changedUser);

    void logIn(String SessionID, UserProfile user);

    void logOut(String SessionID);

    boolean isAuthorized(String SessionID);
}
