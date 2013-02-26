package be.kdg.groeph.dao;

import be.kdg.groeph.model.TripUser;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao {
    static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public boolean addUser(TripUser user) {
        sessionFactory.getCurrentSession().save(user);
        return true;
    }

    @Override
    public TripUser getUserByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from TripUser where email=:email");
        query.setParameter("email",email);
        if (query.uniqueResult()==null) {
            return TripUser.INVALID_USER();
        }
        return (TripUser) query.uniqueResult();
    }

    @Override
    public void updateUser(TripUser user) throws SQLException{
        sessionFactory.getCurrentSession().update(user);
    }


}
