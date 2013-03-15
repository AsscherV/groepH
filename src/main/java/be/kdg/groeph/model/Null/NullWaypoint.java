package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Waypoint;

public class NullWaypoint extends Waypoint implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
