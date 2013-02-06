package be.kdg.groeph.dao;

import be.kdg.groeph.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

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
    public void addUser(User user) {
        getSessionFactory().getCurrentSession().saveOrUpdate(user);
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
