package be.kdg.groeph.service;


import be.kdg.groeph.model.Accessory;

public interface AccessoryService {
    boolean addAccessory(Accessory accessory);

    boolean updateAccessory(Accessory accessory);

    boolean deleteAccessory(Accessory accessory);

    Accessory getAccessoryById(int id);
}
