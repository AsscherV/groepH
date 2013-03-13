package be.kdg.groeph.service;

//import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.RepeatingTripType;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;

import java.util.List;

public interface TripService {
    boolean addTrip(Trip trip);

    List<Trip> fetchAllPublicTrips();

    List<TripType> fetchAllTripTypes();

    TripType getTypeByName(String naam);

    void addUserToTrip(Trip trip, TripUser tripUser);

    List<Trip> getTripsByName(String tripName);
    Trip getTripById(int id);

    //List<Label> getLabels(Trip trip);

    List<Trip> getAllInvitedTripsByUser(TripUser user);

    boolean addConfirmedTrip(Trip currentTrip);

    List<Trip> getAllParticipatedTripsByUser(TripUser user);

    boolean updateTrip(Trip Trip);

    void addTripType(TripType tripType);

    List<RepeatingTripType> fetchAllRepeatingTripTypes();


    List<Trip> getAllCreatedTripsByUser(TripUser user);

    RepeatingTripType getRepetitionTypeByName(String repetitionType);
}
