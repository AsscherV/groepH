package be.kdg.groeph.service;

import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;

import java.util.List;


public interface WaypointService {
    boolean addWaypoint(Waypoint waypoint);
    WaypointType getTypeByName(String name);

    List<WaypointType> fetchAllWaypointTypes();
}
