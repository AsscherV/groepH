package be.kdg.groeph.service;

import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;

import java.sql.SQLException;
import java.util.List;


public interface WaypointService {
    boolean addWaypoint(Waypoint waypoint);
    WaypointType getTypeByName(String name);
    Waypoint getWaypointById(int id);

    List<WaypointType> fetchAllWaypointTypes();
    List<Waypoint> fetchTripWaypoints();
    Waypoint getWaypointByLabel(String waypointLabel);
    boolean updateWaypoint(Waypoint waypoint) ;
    boolean deleteWaypoint(Waypoint waypoint) ;
}
