package be.kdg.groeph.bean;

import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.CostService;
//import org.apache.myfaces.custom.fileupload.HtmlInputFileUpload;
//import org.apache.myfaces.custom.fileupload.UploadedFile;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

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

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;

    @NotEmpty()
    Trip currentTrip;

    @NotEmpty(message = "{notempty}")
    String text;

    @NotEmpty(message = "{notempty}")
    String costValue;

    @NotEmpty(message = "{notempty}")
    TripUser tripUser;

    List<Cost> costs;


    public List<Cost> getCosts() {
        setCosts();
        return costs;
    }

    public void setCosts() {
        currentTrip = tripBean.getCurrentTrip();
        costs = costService.getCostsByTripBeanId(currentTrip);
    }

    public void setTripUser() {
        this.tripUser = loginBean.getUser();
    }

    public CostBean() {
    }

    public CostBean(String text, String costValue) {
        this.text = text;
        this.costValue = costValue;
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

    public Trip getCurrentTrip() {
        currentTrip = tripBean.getCurrentTrip();
        return currentTrip;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }

    public void addCost() {
        try {
            tripUser = loginBean.getUser();
            currentTrip = tripBean.getCurrentTrip();
            costService.addCost(text, costValue, tripUser, currentTrip);
        } catch (Exception e) {
            logger.error(e);
        }
    }
}

