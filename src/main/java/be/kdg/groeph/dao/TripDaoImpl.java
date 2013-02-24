package be.kdg.groeph.dao;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripDaoImpl implements TripDao {
    static Logger logger = Logger.getLogger(TripDaoImpl.class);

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public boolean addTrip(Trip trip) {
        //TODO: Tip indien er een Non-UniqueObject exception gethrowed wordt ofwel session clearen ofwel cascade option weghalen bij dat object uit model klasse
        //--> sessionFactory.getCurrentSession().clear();
        sessionFactory.getCurrentSession().saveOrUpdate(trip);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Trip> fetchAllPublicTrips() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Trip where isPublic=:public");
        query.setParameter("public",true);
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TripType> fetchAllTripTypes() {
        Query query = sessionFactory.getCurrentSession().createQuery("from TripType");
        return query.list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public TripType getTypeByName(String naam) {
        Query query = sessionFactory.getCurrentSession().createQuery("from TripType where type=:naam");
        query.setParameter("naam",naam);
        return (TripType) query.uniqueResult();
    }


}
