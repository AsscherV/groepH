package be.kdg.groeph.dao;

//import be.kdg.groeph.model.Label;

import be.kdg.groeph.model.RepeatingTripType;
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
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(trip);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public void addUserToTrip(Trip trip) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(trip);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public boolean updateTrip(Trip trip) {
        try {
            sessionFactory.getCurrentSession().update(trip);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Trip> fetchAllPublicTrips() {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Trip where isPublic=:public");
            query.setParameter("public", true);
            return query.list();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Trip> fetchAllPublicAndPublishedTrips() {
        try {
                        Query query = sessionFactory.getCurrentSession().createQuery("from Trip where visible=:visible and isPublic=:public");
                        query.setParameter("public", true);
                        query.setParameter("visible", true);
                        return query.list();
                    } catch (NullPointerException e) {
                        logger.error(e.getMessage());
                        return null;
                    }
    }



    @Override
    @SuppressWarnings("unchecked")
    public List<TripType> fetchAllTripTypes() {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from TripType");
            return query.list();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public TripType getTypeByName(String naam) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from TripType where type=:naam");
            query.setParameter("naam", naam);
            return (TripType) query.uniqueResult();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addTripType(TripType tripType) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(tripType);
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
        }
    }

    //TODO: Werkt niet
    @Override
    public List<Trip> getTripsByName(String tripName) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Trip where title=:tripName");
            query.setParameter("tripName", tripName);
            return (List<Trip>) query.list();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }


    @Override
    public Trip getTripById(int id) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Trip where id=:id");
            query.setParameter("id", id);
            return (Trip) query.uniqueResult();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Trip> getAllInvitedTripsByUser(TripUser user) {
        try {
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
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Trip> getAllParticipatedTripsByUser(TripUser user) {
        try {
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
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean addConfirmedTrip(Trip currentTrip) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(currentTrip);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Trip> getTripByUserId(TripUser tripUser) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Trip where tripUser=:tripUser");
            query.setParameter("tripUser", tripUser);
            return (List<Trip>) query.list();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<RepeatingTripType> fetchAllRepeatingTripTypes() {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from RepeatingTripType");
            return query.list();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public RepeatingTripType getRepetitionTypeByName(String repetitionType) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from RepeatingTripType where repeatingType=:repetitionType");
            query.setParameter("repetitionType", repetitionType);
            return (RepeatingTripType) query.uniqueResult();

        }   catch (NullPointerException e){
            logger.error(e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<TripUser> getParticipantsByTrip(Trip trip) {
        try {


            Query query = sessionFactory.getCurrentSession().createQuery("from TripUser tripUser left join tripUser.confirmedTrips trips where trips=:id");
            query.setParameter("id", trip);
            List<Object[]> tripUsers = query.list();

            List<TripUser> tripUserList = new ArrayList<TripUser>();
            for (int i = 0; i < tripUsers.size(); i++) {

                Object[] tripArray = tripUsers.get(i);
                if (TripUser.class == tripArray[0].getClass()) {
                    TripUser tripUserToAdd = (TripUser) tripArray[0];
                    tripUserList.add(tripUserToAdd);

                }
            }
            return tripUserList;


        } catch (Exception e) {
            logger.error(e.getMessage().toString());
            return null;
        }
    }


}
