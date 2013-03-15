package be.kdg.groeph.dao;

import be.kdg.groeph.bean.LoginBean;
import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 28/02/13
 * Time: 14:27
 */

@Repository
public class CostDaoImpl implements CostDao {
    static Logger logger = Logger.getLogger(CostDaoImpl.class);
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addCost(Cost cost) {
        try {
            sessionFactory.getCurrentSession().save(cost);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateCost(Cost cost) {
        try {
            sessionFactory.getCurrentSession().update(cost);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteCost(Cost cost) {
        try {
            sessionFactory.getCurrentSession().delete(cost);
            return true;
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Cost> getCostByTrip(Trip trip) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Cost where trip=:trip");
            query.setParameter("trip", trip);
            return (List<Cost>) query.list();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Cost getCostByCostId(int id) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from Cost where id=:id");
            query.setParameter("id", id);
            return (Cost) query.uniqueResult();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Double getTotalCostByTrip(Trip trip) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("SELECT SUM(costValue) from Cost where trip=:trip");
            query.setParameter("trip", trip);
            return (Double) query.uniqueResult();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Double getTotalCostByUser(Trip trip, TripUser tripUser) {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("SELECT SUM(costValue) from Cost where trip=:trip and tripUser=:tripUser");
            query.setParameter("trip", trip);
            query.setParameter("tripUser", tripUser);
            return (Double) query.uniqueResult();
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}
