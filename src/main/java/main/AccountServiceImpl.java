package main;

import database.*;
import org.eclipse.jetty.server.session.JDBCSessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.*;


/**
 * Created by seven-teen on 30.03.16.
 */
public class AccountServiceImpl implements AccountService {
    private final Map<String, UsersDataSet> sessions = new HashMap<>();

    private final SessionFactory factory;

    public AccountServiceImpl() {
        final Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(MusicDataSet.class);
        configuration.addAnnotatedClass(UsersDataSet.class);
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

    /*@Override
    public void printSessions() {
        for (String s : sessions.keySet()) {
            System.out.println(s);
        }
    }*/

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

    @Nullable
    @Override
    public MusicDataSet getTrack(Long id){
        Session session = factory.openSession();
        MusicDAO dao = new MusicDAO(session);
        return dao.getTrack(id);
    }

    @Override
    public ArrayList<MusicDataSet> getTracks() {
        ArrayList<MusicDataSet> tracks = new ArrayList<>(5);
        for (MusicDataSet track : tracks) {
            Random r = new Random();
            track= getTrack((long)r.nextInt(2));
        }
        return tracks;
    }
}
