package be.kdg.groeph.dao;


import be.kdg.groeph.model.Accessory;

public interface AccessoryDao {
    boolean addAccessory(Accessory accessory);

    boolean updateAccessory(Accessory accessory);

    boolean deleteAccessory(Accessory accessory);

    Accessory getAccessoryById(int id);
}
