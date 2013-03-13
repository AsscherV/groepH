package be.kdg.groeph.dao;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WaypointDaoImpl implements WaypointDao {
    static Logger logger = Logger.getLogger(WaypointDaoImpl.class);
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addWaypoint(Waypoint waypoint) {
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(waypoint);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean updateWaypoint(Waypoint waypoint) {
        try {
            sessionFactory.getCurrentSession().update(waypoint);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteWaypoint(Waypoint waypoint) {
        try {
        sessionFactory.getCurrentSession().delete(waypoint);
        return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public List<Waypoint> getWaypointsByTrip(Trip trip) {
        try {
        Query query = sessionFactory.getCurrentSession().createQuery("from Waypoint where trip=:trip");
        query.setParameter("trip", trip);
        return query.list();
        } catch (Exception e){
            logger.error(e);
            return null;
        }
    }

    @Override
    public WaypointType getTypeByName(String name) {
        try {
        Query query = sessionFactory.getCurrentSession().createQuery("from WaypointType where type=:name");
        query.setParameter("name", name);
        return (WaypointType) query.uniqueResult();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public void addWaypointType(WaypointType type) {
        try {
        sessionFactory.getCurrentSession().saveOrUpdate(type);
        } catch (Exception e){
            logger.error(e);
        }
    }


    @Override
    public List<WaypointType> fetchAllWaypointTypes() {
        try {
        Query query = sessionFactory.getCurrentSession().createQuery("from WaypointType");
        return query.list();
        } catch (Exception e){
            logger.error(e);
            return null;
        }
    }


    //TODO: Aanpassen... --> Verwijderen
    @Override
    public Waypoint getWaypointByLabel(String waypointLabel) {
        try {
        Query query = sessionFactory.getCurrentSession().createQuery("from Waypoint where label=:waypointLabel");
        query.setParameter("waypointLabel", waypointLabel);
        return (Waypoint) query.uniqueResult();
        } catch (Exception e){
            logger.error(e);
            return null;
        }
    }

    @Override
    public Waypoint getWaypointById(int id) {
        try {
        Query query = sessionFactory.getCurrentSession().createQuery("from Waypoint where id=:id");
        query.setParameter("id", id);
        return (Waypoint) query.uniqueResult();
        } catch (Exception e){
            logger.error(e);
            return null;
        }
    }

}
