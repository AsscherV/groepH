package be.kdg.groeph.dao;

import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 28/02/13
 * Time: 14:26
 */
public interface CostDao {

    public boolean addCost(Cost cost);

    public boolean updateCost(Cost cost);

    public boolean deleteCost(Cost cost);

    public List<Cost> getCostByTrip(Trip trip);

    public Cost getCostByCostId(int id);

    public Double getTotalCostByTrip(Trip trip);

    public Double getTotalCostByUser(Trip trip, TripUser tripUser);
}
