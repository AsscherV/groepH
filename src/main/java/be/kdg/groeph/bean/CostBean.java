package be.kdg.groeph.bean;

import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.CostService;
//import org.apache.myfaces.custom.fileupload.HtmlInputFileUpload;
//import org.apache.myfaces.custom.fileupload.UploadedFile;
import be.kdg.groeph.service.TripService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 26/02/13
 * Time: 17:15
 */
@Component
@Named
@RequestScoped
public class CostBean implements Serializable {
    static Logger logger = Logger.getLogger(LoginBean.class);

    @Autowired
    CostService costService;

    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Qualifier("tripBean")
    @Autowired
    private TripBean tripBean;


    private Trip currentTrip;
    private Cost currentCost;
    @NotEmpty(message = "{notempty}")
    private String text;
    @NotEmpty(message = "{notempty}")
    private String newtext;
    @NotEmpty(message = "{notempty}")
    private String costValue;
    @NotEmpty(message = "{notempty}")
    private String newcostValue;
    @NotEmpty(message = "{notempty}")
    private TripUser tripUser;

    private Map<Integer, Boolean> editableCosts;
    private List<Cost> costs;
    private Double totalCost;
    private Double totalCostByUser;
    private List<TripUser> thisTripUsers;
    private Double averageCostPerUser;
    private Double calculatedCostPerUser;
    private ArrayList<Cost> calculatedCosts;

    public CostBean() {
        editableCosts = new HashMap<Integer, Boolean>();
    }

    public CostBean(String text, String costValue) {
        this.text = text;
        this.costValue = costValue;
        editableCosts = new HashMap<Integer, Boolean>();
    }

    public List<Cost> getCosts() {
        setCosts();
        return costs;
    }

    public boolean getHasCosts() {
        if (getCosts().size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void setCosts() {
        costs = costService.getCostsByTrip(getCurrentTrip());
    }

    public Double getAverageCostPerUser() {
        totalCost = getTotalCost();
        thisTripUsers = getConfirmedTripUsers();
        if (totalCost != 0) {
            return totalCost / thisTripUsers.size();
        } else {
            return 0.0;
        }
    }

    public Double getTotalCost() {
        Double total = costService.getTotalCostByTrip(currentTrip);
        if (total == null) {
            return 0.0;
        } else {
            return total;
        }
    }

    public List<Cost> getCostsPerTripUser() {
        calculatedCostPerUser = 0.0;
        calculatedCosts = new ArrayList<Cost>();

        totalCost = getTotalCost();

        thisTripUsers = getConfirmedTripUsers();

        averageCostPerUser = getAverageCostPerUser();

        for (TripUser user : thisTripUsers) {
            totalCostByUser = getTotalCostPerUser(user);
            calculatedCostPerUser = averageCostPerUser - totalCostByUser;
            calculatedCosts.add(new Cost("total cost", calculatedCostPerUser, user, currentTrip));
        }

        return calculatedCosts;

    }

    public Double getTotalCostPerUser(TripUser user) {
        totalCostByUser = costService.getTotalCostByUser(currentTrip, user);
        if (totalCostByUser == null) {
            return 0.0;
        } else {
            return totalCostByUser;
        }
    }


    public void setTripUser() {
        this.tripUser = loginBean.getUser();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCostValue() {
        return costValue;
    }

    public void setCostValue(String costValue) {
        this.costValue = costValue;
    }

    public String getNewtext() {
        return newtext;
    }

    public void setNewtext(String newtext) {
        this.newtext = newtext;
    }

    public String getNewcostValue() {
        return newcostValue;
    }

    public void setNewcostValue(String newcostValue) {
        this.newcostValue = newcostValue;
    }

    public Trip getCurrentTrip() {
        currentTrip = tripBean.getCurrentTrip();
        return currentTrip;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }

    public List<TripUser> getConfirmedTripUsers() {
        List<TripUser> confirmedUsers = tripService.getTripById(getCurrentTrip().getId()).getConfirmedTripUsers();
        //TODO: organiser needs to be removed, not a confirmed tripuser
        TripUser organiser = getCurrentTrip().getTripUser();
        confirmedUsers.add(organiser);
        return confirmedUsers;
    }

    public Cost getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(Cost currentCost) {
        this.currentCost = currentCost;
    }

    public void addCost() {
        try {
            tripUser = loginBean.getUser();
            Cost cost = new Cost(text, Double.valueOf(costValue), tripUser, getCurrentTrip());
            costService.addCost(cost);
            setCurrentCost(cost);
            editableCosts.clear();
            text = "";
            costValue = "";
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void editCost(Cost cost) {
        try {
            setCurrentCost(cost);
            newtext = currentCost.getText();
            newcostValue = String.valueOf(currentCost.getCostValue());
            editableCosts.put(currentCost.getId(), true);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void updateCost() {
        try {
            editableCosts.clear();
            currentCost.setText(newtext);
            currentCost.setCostValue(Double.valueOf(newcostValue));
            if (costService.updateCost(currentCost)) {
                text = null;
                costValue = null;
            }

        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void cancel(Cost cost) {
        try {
            setCurrentCost(cost);
            newtext = "";
            newcostValue = "";
            editableCosts.clear();
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void deleteCost(Cost cost) {
        try {
            setCurrentCost(cost);
            editableCosts.clear();
            if (costService.deleteCost(currentCost)) {

            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public Boolean getEditable(Cost cost) {
        return editableCosts.get(cost.getId());
    }

    public boolean getContainsEditable() {
        return editableCosts.containsValue(true);
    }

    public boolean isOrganizer() {
        try {
            if (loginBean.getUser().getId() == getCurrentTrip().getTripUser().getId()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }

}

