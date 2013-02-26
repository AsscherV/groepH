package be.kdg.groeph.service;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
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
    //static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LoginServiceImpl.class);

    @Qualifier("tripDaoImpl")
    @Autowired
    TripDao tripDao;

    @Override
    public boolean addTrip(Trip trip) {
        //hier checken of het nen valid trip is
        logger.info("Trip: " + trip.getTitle() + " created");
        return tripDao.addTrip(trip);
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
}
