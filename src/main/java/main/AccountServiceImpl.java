package main;

import database.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by seven-teen on 30.03.16.
 */
public class AccountServiceImpl implements AccountService {
    private Map<String, UsersDataSet> sessions;

    private final SessionFactory factory;

    public AccountServiceImpl(String dbName) {
        final Configuration configuration = Config.getConfiguration(dbName);
        sessions = new HashMap<>();
        factory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration config) {
        final StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(config.getProperties());
        final ServiceRegistry serviceRegistry = builder.build();
        return config.buildSessionFactory(serviceRegistry);
    }

    @Nullable
    @Override
    public Collection<UsersDataSet> getAllUsers() {
        Session session = factory.openSession();
        UsersDAO dao = new UsersDAO(session);
        return dao.getAllUsers();
    }

    @Override
    public boolean addUser(UsersDataSet user) {
        Session session = factory.openSession();
        UsersDAO dao = new UsersDAO(session);
        if (dao.getUserByEmail(user.getEmail()) == null) {
            dao.addUser(user);
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public UsersDataSet getUser(Long id) {
        Session session = factory.openSession();
        UsersDAO dao = new UsersDAO(session);
        return dao.getUser(id);
    }

    @Override
    @Nullable
    public UsersDataSet getUserBySession(String sessionID) {
        if (sessions.containsKey(sessionID))
            return sessions.get(sessionID);
        return null;
    }

    @Override
    public void printSessions() {
        for (String s : sessions.keySet()) {
            System.out.println(s);
        }
    }

    @Override
    public void deleteUser(Long id) {
        Session session = factory.openSession();
        UsersDAO dao = new UsersDAO(session);
        UsersDataSet user = (dao.getUser(id));
        if(user != null)
            dao.deleteUser(user);
    }

    @Override
    public void updateUser(UsersDataSet user, UsersDataSet changedUser) {
        Session session = factory.openSession();
        UsersDAO dao = new UsersDAO(session);
        dao.updateUser(user, changedUser);
    }

    @Override
    public void logIn(String sessionID, UsersDataSet user) {
        sessions.put(sessionID, user);
    }

    @Override
    public void logOut(String sessionID) {
        sessions.remove(sessionID);
    }

    @Override
    public boolean isAuthorized(String sessionID){
        return sessions.containsKey(sessionID);
    }

    @Nullable
    @Override
    public UsersDataSet getUserByEmail(String email){
        Session session = factory.openSession();
        UsersDAO dao = new UsersDAO(session);
        return dao.getUserByEmail(email);
    }
}

