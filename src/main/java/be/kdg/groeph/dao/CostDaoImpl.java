package be.kdg.groeph.dao;

import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;


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
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addCost(Cost cost) {
        sessionFactory.getCurrentSession().save(cost);
        return true;
    }

    @Override
    public boolean updateCost(Cost cost) {
        sessionFactory.getCurrentSession().update(cost);
        return true;
    }

    @Override
    public boolean deleteCost(Cost cost) {
        sessionFactory.getCurrentSession().delete(cost);
        return true;
    }

    @Override
    public List<Cost> getCostByTrip(Trip trip) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Cost where trip=:trip");
        query.setParameter("trip",trip);
         return (List<Cost>)query.list();
    }

    @Override
    public Cost getCostByCostId(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Cost where id=:id");
        query.setParameter("id",id);
        return (Cost)query.uniqueResult();
    }

    @Override
    public Double getTotalCostByTrip(Trip trip) {
        Query query = sessionFactory.getCurrentSession().createQuery("SELECT SUM(costValue) from Cost where trip=:trip");
                query.setParameter("trip",trip);
                 return (Double) query.uniqueResult();
    }

    @Override
        public Double getTotalCostByUser(Trip trip, TripUser tripUser) {
            Query query = sessionFactory.getCurrentSession().createQuery("SELECT SUM(costValue) from Cost where trip=:trip and tripUser=:tripUser");
            query.setParameter("trip",trip);
            query.setParameter("tripUser",tripUser);
            return (Double) query.uniqueResult();
        }
}
