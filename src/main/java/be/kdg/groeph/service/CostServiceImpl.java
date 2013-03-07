package be.kdg.groeph.service;

import be.kdg.groeph.dao.CostDao;
import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 28/02/13
 * Time: 14:14
 */

@Transactional
@Service("costService")
public class CostServiceImpl implements CostService {
    @Qualifier("costDaoImpl")
    @Autowired
    CostDao costDao;

    @Override
    public void addCost(String CostName, String CostValue, TripUser user, Trip currentTrip) {
        costDao.addCost(CostName, CostValue, user, currentTrip);
    }

    @Override
    public List<Cost> getCostsByTripBeanId(Trip trip) {
        return costDao.getCostByTripId(trip);
    }
    @Override
    public Cost getCostByCostId(int id) {
        return costDao.getCostByCostId(id);
    }

}
