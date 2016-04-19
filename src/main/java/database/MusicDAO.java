package database;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jetbrains.annotations.Nullable;

/**
 * Created by seven-teen on 19.04.16.
 */
public class MusicDAO {
    private final Session session;

    public MusicDAO(Session session) {this.session = session;}

    @Nullable
    public MusicDataSet getTrack(Long id){
        try {
            return session.get(MusicDataSet.class, id);
        }
        catch(HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTrack(MusicDataSet track){
        final Transaction trx = session.beginTransaction();
        try {
            session.save(track);
            trx.commit();
        }
        catch(HibernateException e){
            e.printStackTrace();
            trx.rollback();
        }
    }

    public void deleteTrack(MusicDataSet track){
        final Transaction trx = session.beginTransaction();
        try{
            session.delete(track);
            trx.commit();
        }
        catch (HibernateException e){
            e.printStackTrace();
            trx.rollback();
        }
    }
}
