package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.User;

/**
 * To change this template use File | Settings | File Templates.
 */
public class NullUser extends User implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
