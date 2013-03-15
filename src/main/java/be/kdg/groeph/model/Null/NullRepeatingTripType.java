package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.RepeatingTripType;

public class NullRepeatingTripType extends RepeatingTripType implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
