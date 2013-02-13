package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Trip;

public class NullTrip extends Trip implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
