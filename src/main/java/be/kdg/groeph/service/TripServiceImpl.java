package be.kdg.groeph.service;

import be.kdg.groeph.model.Trip;
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
    @Override
    public boolean addTrip(Trip trip) {
        if(trip.getTitle()==null){
            return false;
        } else {
             return true;
        }


    }
}
