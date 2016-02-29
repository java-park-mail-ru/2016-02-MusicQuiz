package main;

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
        users.put(0L, new UserProfile(0L, "admin", "admin", "admin@addm.in"));
        users.put(1L, new UserProfile(1L, "guest", "12345", "guest@gue.st"));
    }

    public Collection<UserProfile> getAllUsers() {
        return users.values();
    }

    public boolean addUser(UserProfile userProfile) {
        if (users.containsKey(userProfile.getID()))
            return false;
        users.put(userProfile.getID(), userProfile);
        return true;
    }

    public UserProfile getUser(Long id) {
        return users.get(id);
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

    /*public void updateUser() {

    }

    public UserProfile getUserByLogin(String login) {

    }

    public UserProfile getUserByID(Long ID) {

    }

    public UserProfile getUserByEmail(String mail) {

    }*/
}
