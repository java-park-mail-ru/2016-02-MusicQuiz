package main;

import database.MusicDataSet;
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
    UsersDataSet getUserBySession(String sessionID);

    void deleteUser(Long id);

    /* for debug
    @SuppressWarnings("unused")
    void printSessions();
    */

    void updateUser(UsersDataSet user, UsersDataSet changedUser);

    void logIn(String sessionID, UsersDataSet user);

    void logOut(String sessionID);

    boolean isAuthorized(String sessionID);

    @Nullable
    UsersDataSet getUserByEmail(String email);

    @Nullable
    MusicDataSet getTrack(Long id);
}
