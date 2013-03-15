package be.kdg.groeph.model.Null;

import be.kdg.groeph.model.Answer;

public class NullAnswer extends Answer implements Nullable{
    @Override
    public boolean isNull() {
        return true;
    }
}
