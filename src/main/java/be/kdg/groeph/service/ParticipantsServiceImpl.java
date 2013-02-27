package be.kdg.groeph.service;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Greg
 * Date: 22/02/13
 * Time: 13:19
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("participantsService")
public class ParticipantsServiceImpl implements ParticipantsService {

    @Autowired
    UserService userService;

    @Autowired
    TripService tripService;

    @Override
    public void addUsersToTrip(ArrayList<String> validEmails, Trip trip) {

        for (int i = 0; i < validEmails.size(); i++) {
            TripUser tripUser = userService.getUserByEmail(validEmails.get(i));
            trip.addTripUser(tripUser);
            tripService.addUserToTrip(trip);
        }
    }
}
