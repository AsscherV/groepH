package be.kdg.groeph.service;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import java.util.List;

@Transactional
@Service("tripService")
public class TripServiceImpl implements TripService {
    static Logger logger = Logger.getLogger(TripServiceImpl.class);
    //static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoginServiceImpl.class);

    @Qualifier("tripDaoImpl")
    @Autowired
    TripDao tripDao;

    @Qualifier("userDaoImpl")
    @Autowired
    UserDao userDao;

    @Override
    public boolean addTrip(Trip trip) {
        logger.info("Trip: " + trip.getTitle() + " created");
        return tripDao.addTrip(trip);
    }

    @Override
    public boolean updateTrip(Trip trip) {
        return tripDao.updateTrip(trip);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Trip> fetchAllPublicTrips() {
        return  tripDao.fetchAllPublicTrips();
    }

    @Override
    public List<TripType> fetchAllTripTypes() {
        return tripDao.fetchAllTripTypes();
    }

    @Override
    public TripType getTypeByName(String naam) {
        return tripDao.getTypeByName(naam);
    }

    @Override
    public void addUserToTrip(Trip trip) {
        userDao.addInvitedUser(trip.getTripUser());
       //tripDao.addUserToTrip(trip);
    }

    @Override
    public Trip getTripByName(String tripName) {
        return tripDao.getTripByName(tripName);
    }
    @Override
    public List<Label> getLabels(Trip trip) {
        return tripDao.getLabels(trip);
    }

    @Override
    public List<Trip> getAllInvitedTripsByUser(TripUser user) {
        return tripDao.getAllInvitedTripsByUser(user);
    }

    @Override
    public List<Trip> getAllParticipatedTripsByUser(TripUser user) {
        return tripDao.getAllParticipatedTripsByUser(user);
    }

    @Override
    public Trip getTripById(int id) {
        return tripDao.getTripById(id);
    }

    @Override
    public boolean addConfirmedTrip(Trip currentTrip) {
        return tripDao.addConfirmedTrip(currentTrip);
    }
}
