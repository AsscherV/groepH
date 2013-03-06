package be.kdg.groeph.dao;

import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class WaypointDaoImpl implements WaypointDao{
    static Logger logger = Logger.getLogger(WaypointDaoImpl.class);
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addWaypoint(Waypoint waypoint) {
        sessionFactory.getCurrentSession().save(waypoint);
        return true;
    }

    @Override
    public WaypointType getTypeByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("from WaypointType where type=:name");
        query.setParameter("name",name);
        return (WaypointType) query.uniqueResult();
    }

    @Override
    public void addWaypointType(WaypointType type) {
        sessionFactory.getCurrentSession().saveOrUpdate(type);
    }

    @Override
    public boolean updateWaypoint(Waypoint waypoint)  {
        sessionFactory.getCurrentSession().update(waypoint);
        return true;
    }

    @Override
    public List<WaypointType> fetchAllWaypointTypes() {
        Query query = sessionFactory.getCurrentSession().createQuery("from WaypointType");
        return query.list();
    }

    @Override
    public List<Waypoint> fetchAllWaypoints() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO: Aanpassen... --> Verwijderen
    @Override
    public Waypoint getWaypointByLabel(String waypointLabel) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Waypoint where label=:waypointLabel");
        query.setParameter("waypointLabel",waypointLabel);
        return (Waypoint) query.uniqueResult();
    }

    @Override
    public Waypoint getWaypointById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Waypoint where id=:id");
        query.setParameter("id",id);
        return (Waypoint) query.uniqueResult();
    }

}
