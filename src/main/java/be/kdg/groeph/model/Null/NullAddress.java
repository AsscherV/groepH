package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Address;

public class NullAddress extends Address implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
