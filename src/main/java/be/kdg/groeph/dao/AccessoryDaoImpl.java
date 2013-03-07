package be.kdg.groeph.dao;

import be.kdg.groeph.model.Accessory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AccessoryDaoImpl implements AccessoryDao {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean addAccessory(Accessory accessory) {
        sessionFactory.getCurrentSession().saveOrUpdate(accessory);
        return true;
    }
}
