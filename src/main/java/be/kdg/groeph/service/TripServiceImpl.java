package be.kdg.groeph.service;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.Trip;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 8/02/13
 * Time: 11:53
 */
@Transactional
@Service("tripService")
public class TripServiceImpl implements TripService {
    static Logger logger = Logger.getLogger(TripServiceImpl.class);

    @Autowired
    TripDao tripDao;

    @Override
    public boolean addTrip(Trip trip) {
        //hier checken of het nen valid trip is
        return tripDao.addTrip(trip);
    }
}
