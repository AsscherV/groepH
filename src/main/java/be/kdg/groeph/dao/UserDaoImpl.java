package be.kdg.groeph.dao;

import be.kdg.groeph.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    static Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean addUser(User user) {
        getSessionFactory().getCurrentSession().saveOrUpdate(user);
        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where email=:email");
        query.setParameter("email",email);
        if (query.uniqueResult()==null) {
            return User.INVALID_USER();
        }
        return (User) query.uniqueResult();
    }

}
