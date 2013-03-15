package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Cost;

public class NullCost extends Cost implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
