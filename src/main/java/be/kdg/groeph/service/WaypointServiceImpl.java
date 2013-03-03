package be.kdg.groeph.service;

import be.kdg.groeph.dao.WaypointDao;
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
public class WaypointServiceImpl implements WaypointService{
    static Logger logger = Logger.getLogger(WaypointServiceImpl.class);
    @Qualifier("waypointDaoImpl")
    @Autowired
    WaypointDao waypointDao;
    @Override
    public boolean addWaypoint(Waypoint waypoint) {
        logger.info("Waypoint: " + waypoint.getLabel() + " created");
        return waypointDao.addWaypoint(waypoint);
    }

    @Override
    public WaypointType getTypeByName(String name) {
        return waypointDao.getTypeByName(name);
    }

    @Override
    public List<WaypointType> fetchAllWaypointTypes() {
        return waypointDao.fetchAllWaypointTypes();
    }

    @Override
    public List<Waypoint> fetchTripWaypoints() {
        return waypointDao.fetchAllWaypoints();
    }

    @Override
    public Waypoint getWaypointByLabel(String waypointLabel) {
        return waypointDao.getWaypointByLabel(waypointLabel);
    }

    @Override
    public Waypoint getWaypointById(int id) {
        return waypointDao.getWaypointById(id);
    }

}
