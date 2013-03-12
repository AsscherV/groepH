package be.kdg.groeph.dao;


import be.kdg.groeph.model.Accessory;
import be.kdg.groeph.model.Trip;

import java.util.List;

public interface AccessoryDao {
    boolean addAccessory(Accessory accessory);

    boolean updateAccessory(Accessory accessory);

    boolean deleteAccessory(Accessory accessory);

    Accessory getAccessoryById(int id);

    List<Accessory> getAccessoriesByTrip(Trip trip);
}
