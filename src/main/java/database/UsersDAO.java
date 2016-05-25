package database;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by seven-teen on 05.04.16.
 */
public class UsersDAO {
    private final Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    @Nullable
    public UsersDataSet getUser(Long id) throws HibernateException {
        return session.get(UsersDataSet.class, id);
    }

    public void addUser(UsersDataSet user) throws HibernateException {
        session.save(user);
    }

    public void updateUser(UsersDataSet currentUser, UsersDataSet newUser) throws HibernateException {
        currentUser.setEmail(newUser.getEmail());
        currentUser.setLogin(newUser.getLogin());
        currentUser.setPassword(newUser.getPassword());
        session.update(currentUser);
    }

    public void deleteUser(UsersDataSet user) throws HibernateException {
        session.delete(user);
    }

    @Nullable
    public UsersDataSet getUserByEmail(String email) throws HibernateException {
        final Criteria criteria = session.createCriteria(UsersDataSet.class);
        return (UsersDataSet) criteria.add(Restrictions.eq("email", email)).uniqueResult();
    }

    @Nullable
    public List<UsersDataSet> getTopUsers(int id) throws HibernateException {
        final Criteria criteria = session.createCriteria(UsersDataSet.class)
                .addOrder(Order.desc("points"))
                .setFirstResult(0)
                .setMaxResults(id);
        return (List<UsersDataSet>) criteria.list();
    }
}
