package be.kdg.groeph.service;

import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 28/02/13
 * Time: 14:14
 */
public interface CostService {

    public void addCost(String CostName, String CostValue, TripUser user, Trip currentTrip);
    public List<Cost> getCostsByTripBeanId(Trip trip);

}
