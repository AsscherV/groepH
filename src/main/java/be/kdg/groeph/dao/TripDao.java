package be.kdg.groeph.dao;

import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;

import java.util.List;

public interface TripDao {
    boolean addTrip(Trip trip);

    void addUserToTrip(Trip trip);
    List<Trip> fetchAllPublicTrips();

    List<TripType> fetchAllTripTypes();

    TripType getTypeByName(String naam);
    void addTripType(TripType tripType);

    List<Trip> getTripsByName(String tripName);

    List<Label> getLabels(Trip trip);

    Trip getTripById(int id);

    List<Trip> getAllInvitedTripsByUser(TripUser user);

    boolean addConfirmedTrip(Trip currentTrip);

    List<Trip> getAllParticipatedTripsByUser(TripUser user);

    boolean updateTrip(Trip trip);

    List<Trip> getTripByUserId(TripUser tripUser);
}