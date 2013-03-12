package be.kdg.groeph.service;

import be.kdg.groeph.dao.AccessoryDao;
import be.kdg.groeph.model.Accessory;
import be.kdg.groeph.model.Trip;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("accessoryService")
public class AccessoryServiceImpl implements  AccessoryService{
    static Logger logger = Logger.getLogger(AccessoryService.class);
    @Qualifier("accessoryDaoImpl")
    @Autowired
    AccessoryDao accessoryDao;

    @Override
    public boolean addAccessory(Accessory accessory) {
        logger.info("Accessory: " + accessory.getDescription() + " created");
        return accessoryDao.addAccessory(accessory);
    }

    @Override
    public boolean updateAccessory(Accessory accessory) {
        return accessoryDao.updateAccessory(accessory);
    }

    @Override
    public boolean deleteAccessory(Accessory accessory) {
        return accessoryDao.deleteAccessory(accessory);
    }

    @Override
    public Accessory getAccessoryById(int id) {
        return accessoryDao.getAccessoryById(id);
    }

    @Override
    public List<Accessory> getAccessoriesByTrip(Trip trip) {
        return accessoryDao.getAccessoriesByTrip(trip);
    }
}
