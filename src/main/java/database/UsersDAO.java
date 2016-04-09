package database;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Created by seven-teen on 05.04.16.
 */
public class UsersDAO {
    private final Session session;

    public UsersDAO(Session session) {this.session = session;}

    @Nullable
    public UsersDataSet getUser(Long id){
        try {
            return session.get(UsersDataSet.class, id);
        }
        catch(HibernateException e) {
            e.printStackTrace();
        }
            return null;
    }

    public void addUser(UsersDataSet user){
        final Transaction trx = session.beginTransaction();
        try {
            session.save(user);
            trx.commit();
        }
        catch(HibernateException e){
            e.printStackTrace();
            trx.rollback();
        }
    }

    public void updateUser(UsersDataSet currentUser, UsersDataSet newUser){
        final Transaction trx = session.beginTransaction();
        try{
            currentUser.setEmail(newUser.getEmail());
            currentUser.setLogin(newUser.getLogin());
            currentUser.setPassword(newUser.getPassword());
            session.update(currentUser);
            trx.commit();
        }
        catch (HibernateException e){
            e.printStackTrace();
            trx.rollback();
        }
    }

    public void deleteUser(UsersDataSet user){
        final Transaction trx = session.beginTransaction();
        try{
            session.delete(user);
            trx.commit();
        }
        catch (HibernateException e){
            e.printStackTrace();
            trx.rollback();
        }
    }

    @Nullable
    public UsersDataSet getUserByEmail(String email){
        final Criteria criteria = session.createCriteria(UsersDataSet.class);
        try{
            return (UsersDataSet)criteria.add(Restrictions.eq("email", email)).uniqueResult();
        }
        catch(HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public Collection<UsersDataSet> getAllUsers() {
        final Criteria criteria = session.createCriteria(UsersDataSet.class);
        try {
            return (List<UsersDataSet>)criteria.list();
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
        return null;
    }
}
