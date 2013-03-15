package be.kdg.groeph.dao;

import be.kdg.groeph.bean.LoginBean;
import be.kdg.groeph.model.Accessory;
import be.kdg.groeph.model.Trip;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccessoryDaoImpl implements AccessoryDao {
    static Logger logger = Logger.getLogger(AccessoryDaoImpl.class);

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addAccessory(Accessory accessory) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(accessory);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateAccessory(Accessory accessory) {
        try {
            sessionFactory.getCurrentSession().update(accessory);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteAccessory(Accessory accessory) {
        try {
            sessionFactory.getCurrentSession().delete(accessory);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Accessory getAccessoryById(int id) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Accessory where id=:id");
            query.setParameter("id", id);
            return (Accessory) query.uniqueResult();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Accessory> getAccessoriesByTrip(Trip trip) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Accessory where trip=:trip");
            query.setParameter("trip", trip);
            return query.list();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
