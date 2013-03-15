package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.TripType;

public class NullTripType extends TripType implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
