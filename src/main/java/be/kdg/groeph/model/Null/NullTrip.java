package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Trip;

/**
 * To change this template use File | Settings | File Templates.
 */
public class NullTrip extends Trip implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
