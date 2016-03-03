package main;

import org.jetbrains.annotations.Nullable;
import rest.UserProfile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IlyaRogov on 29.02.16.
 */

public class AccountService {
    private Map<Long, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> sessions = new HashMap<>();

    public AccountService() {
        users.put(0L, new UserProfile("admin", "admin", "admin@addm.in"));
        users.put(1L, new UserProfile("guest", "12345", "guest@gue.st"));
    }

    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

    public boolean addUser(UserProfile userProfile) {
        if(users.containsKey(userProfile.getID()))
            return false;
        users.put(userProfile.getID(), userProfile);
        return true;
    }

    public UserProfile getUser(Long id) {
        return users.get(id);
    }

    @Nullable
    public UserProfile getUserBySession(String SessionID) {
        if(sessions.containsKey(SessionID)) {
            return sessions.get(SessionID);
        }
        else return null;
    }

    public Boolean deleteUser(Long id) {
        if(users.containsKey(id)) {
            users.remove(id);
            return true;
        }
        else {
            return false;
        }
    }

    public void updateUser(UserProfile user, UserProfile changedUser) {
        if(!user.getLogin().equals(changedUser.getLogin()) && changedUser.getLogin() != null) {
                user.setLogin(changedUser.getLogin());
        }

        if(!user.getPassword().equals(changedUser.getPassword()) && changedUser.getPassword() != null) {
            user.setPassword(changedUser.getPassword());
        }

        if(!user.getEmail().equals(changedUser.getEmail()) && changedUser.getEmail() != null) {
            user.setEmail(changedUser.getEmail());
        }

    }

    public void logIn(String SessionID, UserProfile user) {
        sessions.put(SessionID, user);
    }

    public Boolean isAuthenticated(String SessionID, UserProfile user) {

        return sessions.get(SessionID).getID().equals(user.getID());
    }

    public void logOut(String SessionID) {
        sessions.remove(SessionID);
    }
}
