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

    Trip getTripByName(String tripName);

    List<Label> getLabels(Trip trip);
}