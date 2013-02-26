package be.kdg.groeph.service;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;

import java.util.List;

public interface TripService {
    boolean addTrip(Trip trip);

    List<Trip> fetchAllPublicTrips();

    List<TripType> fetchAllTripTypes();

    TripType getTypeByName(String naam);
}
