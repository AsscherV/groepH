package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Accessory;

public class NullAccessory extends Accessory implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
