package be.kdg.groeph.dao;


import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;

import java.util.List;

public interface WaypointDao {
    boolean addWaypoint(Waypoint waypoint);

    WaypointType getTypeByName(String naam);

    void addWaypointType(WaypointType type);

    List<WaypointType> fetchAllWaypointTypes();
    List<Waypoint> fetchAllWaypoints();
}
