package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Address;
/**
 * To change this template use File | Settings | File Templates.
 */
public class NullAddress extends Address implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
