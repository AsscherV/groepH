package be.kdg.groeph.service;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;

import java.util.List;


public interface WaypointService {
    boolean addWaypoint(Waypoint waypoint);
    WaypointType getTypeByName(String name);
    Waypoint getWaypointById(int id);

    List<WaypointType> fetchAllWaypointTypes();
    Waypoint getWaypointByLabel(String waypointLabel);
    boolean updateWaypoint(Waypoint waypoint) ;
    boolean deleteWaypoint(Waypoint waypoint) ;

    List<Waypoint> getWaypointsByTrip(Trip trip);
}
