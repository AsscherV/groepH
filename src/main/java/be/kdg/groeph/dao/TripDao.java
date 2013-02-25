package be.kdg.groeph.dao;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;

import java.util.List;

public interface TripDao {
    boolean addTrip(Trip trip);

    List<Trip> fetchAllPublicTrips();

    List<TripType> fetchAllTripTypes();

    TripType getTypeByName(String naam);
    void addTripType(TripType tripType);
}