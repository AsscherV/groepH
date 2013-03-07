package be.kdg.groeph.service;

import be.kdg.groeph.dao.AccessoryDao;
import be.kdg.groeph.model.Accessory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
