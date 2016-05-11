package main;

import database.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.hibernate.service.ServiceRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by seven-teen on 30.03.16.
 */
public class AccountServiceImpl implements AccountService {
    private final Map<String, UsersDataSet> sessions = new HashMap<>();

    private final SessionFactory factory;

    public AccountServiceImpl(String file) {
        final Configuration configuration = new Configuration().configure(file);
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
        Transaction tx = null;
        try(Session session = factory.openSession()) {
            tx = session.beginTransaction();
            final UsersDAO dao = new UsersDAO(session);
            return dao.getAllUsers();
        } catch (HibernateException ex) {
            if(tx != null && (tx.getStatus() == TransactionStatus.ACTIVE
                            || tx.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                tx.rollback();
            }
            ex.printStackTrace();
            throw new HibernateException("Unable to get all users", ex);
        }
    }


    @Override
    public boolean addUser(UsersDataSet user) {
        Transaction tx = null;
        try(Session session = factory.openSession()) {
            tx = session.beginTransaction();
            final UsersDAO dao = new UsersDAO(session);
            if (dao.getUserByEmail(user.getEmail()) == null) {
                dao.addUser(user);
                return true;
            }
            tx.commit();
        }
        catch (HibernateException ex){
            if(tx != null && (tx.getStatus() == TransactionStatus.ACTIVE
                            || tx.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                tx.rollback();
            }
            ex.printStackTrace();
            throw new HibernateException("Unable to add user", ex);
        }
        return false;
    }

    @Nullable
    @Override
    public UsersDataSet getUser(Long id) {
        Transaction tx = null;
        try(Session session = factory.openSession()) {
            tx = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            return dao.getUser(id);
        }
        catch (HibernateException ex) {
            if(tx != null && (tx.getStatus() == TransactionStatus.ACTIVE
                    || tx.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                tx.rollback();
            }
        ex.printStackTrace();
        throw new HibernateException("Unable to get user by id", ex);
        }
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
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            UsersDataSet user = (dao.getUser(id));
            if (user != null)
                dao.deleteUser(user);
            tx.commit();
        } catch (HibernateException ex) {
            if (tx != null && (tx.getStatus() == TransactionStatus.ACTIVE
                       || tx.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                tx.rollback();
            }
            ex.printStackTrace();
            throw new HibernateException("Unable to delete user", ex);
        }
    }

    @Override
    public void updateUser(UsersDataSet user, UsersDataSet changedUser) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            dao.updateUser(user, changedUser);
            tx.commit();
        }
        catch (HibernateException ex) {
            if (tx != null && (tx.getStatus() == TransactionStatus.ACTIVE
                    || tx.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                tx.rollback();
            }
            ex.printStackTrace();
            throw new HibernateException("Unable to update user", ex);
        }
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
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            UsersDAO dao = new UsersDAO(session);
            return dao.getUserByEmail(email);
        }
        catch (HibernateException ex) {
            if (tx != null && (tx.getStatus() == TransactionStatus.ACTIVE
                    || tx.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                tx.rollback();
            }
            ex.printStackTrace();
            throw new HibernateException("Unable to get user by email", ex);
        }
    }

    @Nullable
    @Override
    public MusicDataSet getTrack(Long id) {
        Transaction tx = null;
        try (Session session = factory.openSession()) {
            tx = session.beginTransaction();
            MusicDAO dao = new MusicDAO(session);
            return dao.getTrack(id);
        }
        catch (HibernateException ex) {
            if (tx != null && (tx.getStatus() == TransactionStatus.ACTIVE
                    || tx.getStatus() == TransactionStatus.MARKED_ROLLBACK)) {
                tx.rollback();
            }
            ex.printStackTrace();
            throw new HibernateException("Unable to get track", ex);
        }
    }
}

