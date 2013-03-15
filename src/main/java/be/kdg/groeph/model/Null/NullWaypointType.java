package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.WaypointType;

public class NullWaypointType extends WaypointType implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
