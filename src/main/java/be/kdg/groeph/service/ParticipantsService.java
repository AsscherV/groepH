package be.kdg.groeph.service;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Greg
 * Date: 22/02/13
 * Time: 13:18
 * To change this template use File | Settings | File Templates.
 */
public interface ParticipantsService {
    void addUsersToTrip(ArrayList<String> validEmails, Trip trip);
}
