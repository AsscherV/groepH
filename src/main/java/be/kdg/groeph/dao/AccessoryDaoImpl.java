package be.kdg.groeph.dao;

import be.kdg.groeph.model.Accessory;
import org.hibernate.Query;
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

    @Override
    public boolean updateAccessory(Accessory accessory) {
        sessionFactory.getCurrentSession().update(accessory);
        return true;
    }

    @Override
    public boolean deleteAccessory(Accessory accessory) {
        sessionFactory.getCurrentSession().delete(accessory);
        return true;
    }

    @Override
    public Accessory getAccessoryById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Accessory where id=:id");
        query.setParameter("id",id);
        return (Accessory) query.uniqueResult();
    }
}
