package main;

import rest.UserProfile;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

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

    /*public UserProfile getUser(Long id) {
        return users.get(id);
    }*/

    public Boolean deleteUser(Long id) {
        if(users.containsKey(id)) {
            users.remove(id);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean updateUser(Long ID, UserProfile user) {
        if(users.containsKey(ID)) {
            UserProfile current_user = getUserByID(ID);
            user.setLogin(user.getLogin());
            current_user.setPassword(user.getPassword());
            current_user.setEmail(user.getEmail());
            return true;
        }
        else return false;
    }
    /*public UserProfile getUserByLogin(String login) {
        return users.get(1);
    }*/
    //зачем этот метод? user`ов же так неудобно доставать?

    public UserProfile getUserByID(Long ID) {
        return users.get(ID);
    }

    /*public UserProfile getUserByEmail(String mail) {
        return users.get(1);
    }*/
    //и этот?
}
