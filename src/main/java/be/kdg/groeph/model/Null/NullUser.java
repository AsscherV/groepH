package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.TripUser;

/**
 * To change this template use File | Settings | File Templates.
 */
public class NullUser extends TripUser implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
