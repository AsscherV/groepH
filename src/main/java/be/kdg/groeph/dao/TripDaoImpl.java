package be.kdg.groeph.dao;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class TripDaoImpl implements TripDao {
    static Logger logger = Logger.getLogger(TripDaoImpl.class);

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    /*

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    */

    @Override
    @SuppressWarnings("unchecked")
    public boolean addTrip(Trip trip) {
        sessionFactory.getCurrentSession().saveOrUpdate(trip);
        return true;
    }

}
