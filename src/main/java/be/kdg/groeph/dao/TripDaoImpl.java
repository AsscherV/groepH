package be.kdg.groeph.dao;

import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.model.TripType;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        sessionFactory.getCurrentSession().saveOrUpdate(trip);
        return true;
    }

    @Override
    public void addUserToTrip(Trip trip) {
        sessionFactory.getCurrentSession().saveOrUpdate(trip);
    }

    @Override
    public boolean updateTrip(Trip trip) {
        sessionFactory.getCurrentSession().update(trip);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Trip> fetchAllPublicTrips() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Trip where isPublic=:public");
        query.setParameter("public", true);
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
        query.setParameter("naam", naam);
        return (TripType) query.uniqueResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addTripType(TripType tripType) {
        sessionFactory.getCurrentSession().saveOrUpdate(tripType);
    }

    //TODO: Werkt niet
    @Override
    public Trip getTripByName(String tripName) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Trip where title=:tripName");
        query.setParameter("tripName", tripName);
        return (Trip) query.uniqueResult();
    }

    @Override
    public List<Label> getLabels(Trip trip) {
        if (trip != null) {
            Query query = sessionFactory.getCurrentSession().createQuery("from Label where trip=:trip");
            query.setParameter("trip", trip);

            return (List<Label>) query.list();
        }
        return null;
    }

    @Override
    public Trip getTripById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Trip where id=:id");
        query.setParameter("id", id);
        return (Trip) query.uniqueResult();
    }

    @Override
    public List<Trip> getAllInvitedTripsByUser(TripUser user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Trip trip left join trip.tripUsers tripUsers where tripUsers =:id");
        query.setParameter("id", user);
        List<Object[]> trips = query.list();

        List<Trip> returnTripList = new ArrayList<Trip>();
        for (int i = 0; i < trips.size(); i++) {

            Object[] tripArray = trips.get(i);
            if (Trip.class == tripArray[0].getClass()) {
                Trip tripToAdd = (Trip) tripArray[0];
                returnTripList.add(tripToAdd);
            }
        }
        return returnTripList;
    }

    @Override
    public List<Trip> getAllParticipatedTripsByUser(TripUser user) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Trip trip left join trip.confirmedTripUsers tripUsers where tripUsers =:id");
        query.setParameter("id", user);
        List<Object[]> trips = query.list();

        List<Trip> returnTripList = new ArrayList<Trip>();
        for (int i = 0; i < trips.size(); i++) {

            Object[] tripArray = trips.get(i);
            if (Trip.class == tripArray[0].getClass()) {
                Trip tripToAdd = (Trip) tripArray[0];
                returnTripList.add(tripToAdd);
            }
        }
        return returnTripList;
    }

    @Override
    public boolean addConfirmedTrip(Trip currentTrip) {
        sessionFactory.getCurrentSession().saveOrUpdate(currentTrip);
        return true;
    }
}
