package database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.jetbrains.annotations.Nullable;

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
        catch(HibernateException e){
            e.printStackTrace();
        }
        return null;
    }

    public void addUser(UsersDataSet user){
        try {
            session.save(user);
        }
        catch(HibernateException e){
            e.printStackTrace();
        }
    }

    public void updateUser(UsersDataSet currentUser, UsersDataSet newUser){
        try{
            currentUser.setEmail(newUser.getEmail());
            currentUser.setLogin(newUser.getLogin());
            currentUser.setPassword(newUser.getPassword());
            session.update(currentUser);
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
    }

    public void deleteUser(UsersDataSet user){
        try{
            session.delete(user);
        }
        catch (HibernateException e){
            e.printStackTrace();
        }
    }
}
