package be.kdg.groeph.service;

import be.kdg.groeph.dao.WaypointDao;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service("waypointService")
public class WaypointServiceImpl implements WaypointService {
    static Logger logger = Logger.getLogger(WaypointServiceImpl.class);
    @Qualifier("waypointDaoImpl")
    @Autowired
    WaypointDao waypointDao;

    @Override
    public boolean addWaypoint(Waypoint waypoint) {
        try {
            return waypointDao.addWaypoint(waypoint);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean updateWaypoint(Waypoint waypoint) {
        try {
            return waypointDao.updateWaypoint(waypoint);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public boolean deleteWaypoint(Waypoint waypoint) {
        try {
            return waypointDao.deleteWaypoint(waypoint);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

    @Override
    public List<Waypoint> getWaypointsByTrip(Trip trip) {
        try {
            return waypointDao.getWaypointsByTrip(trip);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public WaypointType getTypeByName(String name) {
        try {
            return waypointDao.getTypeByName(name);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public List<WaypointType> fetchAllWaypointTypes() {
        try {
            return waypointDao.fetchAllWaypointTypes();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Waypoint getWaypointByLabel(String waypointLabel) {
        try {
            return waypointDao.getWaypointByLabel(waypointLabel);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @Override
    public Waypoint getWaypointById(int id) {
        try {
            return waypointDao.getWaypointById(id);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

}
