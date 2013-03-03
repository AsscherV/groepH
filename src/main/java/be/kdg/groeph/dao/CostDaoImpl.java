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
    public void addCost(String CostName, String CostValue, TripUser user, Trip currentTrip) {
       // Blob blob = sessionFactory.getCurrentSession().getLobHelper().createBlob(image);
        Cost cost = new Cost();
        cost.setText(CostName);
        cost.setCostValue(Integer.parseInt(CostValue));
        cost.setTripUser(user);
        cost.setTrip(currentTrip);
        sessionFactory.getCurrentSession().saveOrUpdate(cost);
    }

    @Override
    public List<Cost> getCostByTripId(Trip trip) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Cost where trip=:id");
        query.setParameter("id",trip);
         return (List<Cost>)query.list();
    }

    @Override
    public Cost getCostByCostId(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Cost where id=:id");
        query.setParameter("id",id);
        return (Cost)query.uniqueResult();
    }
}
