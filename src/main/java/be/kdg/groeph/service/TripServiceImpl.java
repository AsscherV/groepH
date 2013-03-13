package be.kdg.groeph.service;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
//import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.RepeatingTripType;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("tripService")
public class TripServiceImpl implements TripService {
    static Logger logger = Logger.getLogger(TripServiceImpl.class);

    @Qualifier("tripDaoImpl")
    @Autowired
    TripDao tripDao;

    @Qualifier("userDaoImpl")
    @Autowired
    UserDao userDao;

    @Override
    public boolean addTrip(Trip trip) {
        try {
            logger.info("Trip: " + trip.getTitle() + " created");
            return tripDao.addTrip(trip);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean updateTrip(Trip trip) {
        try {
            return tripDao.updateTrip(trip);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Trip> fetchAllPublicTrips() {
        try {
            return tripDao.fetchAllPublicTrips();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<TripType> fetchAllTripTypes() {
        return tripDao.fetchAllTripTypes();
    }

    @Override
    public TripType getTypeByName(String naam) {
        try {
            return tripDao.getTypeByName(naam);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public void addUserToTrip(Trip trip, TripUser user) {
        try {
            userDao.addInvitedUser(user);
            tripDao.addUserToTrip(trip);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @Override
    public List<Trip> getTripsByName(String tripName) {
        try {
            return tripDao.getTripsByName(tripName);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    /*@Override
    public List<Label> getLabels(Trip trip) {
        return tripDao.getLabels(trip);
    }  */

    @Override
    public List<Trip> getAllInvitedTripsByUser(TripUser user) {
        try {
            return tripDao.getAllInvitedTripsByUser(user);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<Trip> getAllParticipatedTripsByUser(TripUser user) {
        try {
            return tripDao.getAllParticipatedTripsByUser(user);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Trip getTripById(int id) {
        try {
            return tripDao.getTripById(id);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public boolean addConfirmedTrip(Trip currentTrip) {
        try {
            return tripDao.addConfirmedTrip(currentTrip);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public void addTripType(TripType tripType) {
        try {
            tripDao.addTripType(tripType);
        } catch (Exception e) {
            logger.error(e);
        }

    }

    @Override
    public List<RepeatingTripType> fetchAllRepeatingTripTypes() {
        try {
            return tripDao.fetchAllRepeatingTripTypes();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<Trip> getAllCreatedTripsByUser(TripUser user) {
        try {
            return tripDao.getTripByUserId(user);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public RepeatingTripType getRepetitionTypeByName(String repetitionType) {
        try {
             return tripDao.getRepetitionTypeByName(repetitionType);
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

}
