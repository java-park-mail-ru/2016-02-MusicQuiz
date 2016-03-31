package main;

import org.jetbrains.annotations.Nullable;
import rest.UserProfile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by seven-teen on 30.03.16.
 */
public class AccountServiceImpl implements AccountService {
    private Map<Long, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> sessions = new HashMap<>();

    @Override
    public boolean addUser(UserProfile userProfile) {
        if(users.containsKey(userProfile.getID()))
            return false;
        users.put(userProfile.getID(), userProfile);
        return true;
    }

    @Override
    public UserProfile getUser(Long id) {
        return users.get(id);
    }

    @Override
    @Nullable
    public UserProfile getUserBySession(String SessionID) {
        if(sessions.containsKey(SessionID)) {
            return sessions.get(SessionID);
        }
        else return null;
    }

    public void print_sessions() {
        for (String s : sessions.keySet()) {
            System.out.println(s);
        }
    }

    @Override
    @Nullable
    public UserProfile getUserByLogin(String login){
        for(UserProfile user : users.values()){
            if(user.getLogin().equals(login)){
                return user;
            }
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        users.remove(id);
    }

    @Override
    public void updateUser(UserProfile user, UserProfile changedUser) {
        if(!user.getLogin().equals(changedUser.getLogin())) {
            user.setLogin(changedUser.getLogin());
        }

        if(!user.getPassword().equals(changedUser.getPassword())) {
            user.setPassword(changedUser.getPassword());
        }

        if(!user.getEmail().equals(changedUser.getEmail())) {
            user.setEmail(changedUser.getEmail());
        }

    }

    @Override
    public void logIn(String SessionID, UserProfile user) {
        sessions.put(SessionID, user);
    }

    @Override
    public void logOut(String SessionID) {
        sessions.remove(SessionID);
    }

    @Override
    public boolean isAuthorized(String SessionID){
        return sessions.containsKey(SessionID);
    }
}

