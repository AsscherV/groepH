package be.kdg.groeph.util;

import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class Tools {
    public static List<Trip> filter(List<Trip> trips, String filter) {
        List<Trip> filteredTrips = new ArrayList<Trip>();
        //System.out.println("FILTER: "+filter);
        if(!filter.isEmpty())
        {
            filter=filter.toLowerCase();
            for(Trip t:trips)
            {
                if(t.getTripType().getType().toLowerCase().contains(filter)||t.getTitle().toLowerCase().contains(filter)||t.getLabels().contains(new Label(filter)))
                {
                    filteredTrips.add(t);
                }
            }
        }
        else {
            filteredTrips=trips;
        }
        return filteredTrips;
    }
}
