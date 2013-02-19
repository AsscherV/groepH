package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.TripUser;

public class NullUser extends TripUser implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
