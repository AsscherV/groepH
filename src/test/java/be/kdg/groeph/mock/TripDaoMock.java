package be.kdg.groeph.mock;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TripDaoMock implements TripDao {
    private List<Trip> trips;
    private List<TripType> tripTypes;
    int i = 0;

    public TripDaoMock() {
        trips = new ArrayList<Trip>();
        //tripTypes.add(new TripType("Timebound"));
        //tripTypes.add(new TripType("type2"));
        //tripTypes.add(new TripType("type3"));
        //tripTypes.add(new TripType("type4"));
    }


    @Override
    public boolean addTrip(Trip trip) {
        trip.setId(i);
        i++;
        trips.add(trip);
        return true;
    }

    @Override
    public void addUserToTrip(Trip trip) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Trip> fetchAllPublicTrips() {
        List<Trip> publicTrips = new ArrayList<Trip>();
        for (Trip trip : trips) {
            if (trip.isPublic()) {
                publicTrips.add(trip);
            }
        }
        return publicTrips;
    }

    @Override
    public List<TripType> fetchAllTripTypes() {
        return tripTypes;
    }

    @Override
    public TripType getTypeByName(String naam) {
        for (TripType tripType : tripTypes) {
            if (tripType.getType().equals(naam)) {
                return tripType;
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public void addTripType(TripType tripType) {
        tripTypes.add(tripType);
    }
}
