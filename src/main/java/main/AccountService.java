package main;

import database.UsersDataSet;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * Created by IlyaRogov on 29.02.16.
 */

public interface AccountService {

    Collection<UsersDataSet> getAllUsers();

    boolean addUser(UsersDataSet userProfile);

    UsersDataSet getUser(Long id);

    @Nullable
    UsersDataSet getUserBySession(String SessionID);

    @Nullable
    UsersDataSet getUserByLogin(String login);

    void deleteUser(Long id);

    void print_sessions();

    void updateUser(UsersDataSet user, UsersDataSet changedUser);

    void logIn(String SessionID, UsersDataSet user);

    void logOut(String SessionID);

    boolean isAuthorized(String SessionID);
}
