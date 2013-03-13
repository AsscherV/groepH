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
    public boolean addCost(Cost cost) {
        return costDao.addCost(cost);
    }

    @Override
    public boolean updateCost(Cost cost) {
        return costDao.updateCost(cost);
    }

    @Override
    public boolean deleteCost(Cost cost) {
        return costDao.deleteCost(cost);
    }

    @Override
    public List<Cost> getCostsByTrip(Trip trip) {
        return costDao.getCostByTrip(trip);
    }
    @Override
    public Cost getCostByCostId(int id) {
        return costDao.getCostByCostId(id);
    }

    @Override
    public Double getTotalCostByTrip(Trip trip) {
        return costDao.getTotalCostByTrip(trip);
    }

    @Override
    public Double getTotalCostByUser(Trip trip, TripUser tripUser) {
        return costDao.getTotalCostByUser(trip, tripUser);
    }
}
