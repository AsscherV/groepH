package be.kdg.groeph.bean;

import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.service.TripService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Component
@SessionScoped
@Named
public class TripBean implements Serializable {
    static Logger logger = Logger.getLogger(TripBean.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @NotEmpty(message = "{title} {notempty}")
    private String title;
    @NotEmpty(message = "{description} {notempty}")
    private String description;
    @NotNull(message = "{startTime} {notempty}")
    private Date startTime;
    @NotNull(message = "{endTime} {notempty}")
    private Date endTime;
    @NotEmpty(message = "{label} {notempty}")
    private String label;
    private ArrayList<Label> labels = new ArrayList<Label>();
    @NotEmpty(message = "{tripType} {notempty}")
    private String tripType;
    private String isPublic;

    Trip currentTrip;



    public TripBean() {
    }

    public String getPublic() {
        return isPublic;
    }

    public void setPublic(String aPublic) {
        isPublic = aPublic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartTime(Date time) {
        this.startTime = time;

    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    public void setCurrentTrip(Trip currentTrip) {
        this.currentTrip = currentTrip;
    }

    //TODO: Callen met ajax call
    public void newLabel() {
        labels.add(new Label(label));
        label = "";
    }

    public String addTrip() {
        boolean p;
        if(isPublic.equalsIgnoreCase("public")){
             p = true;
        } else {
             p =  false;
        }

        Trip trip = new Trip(getTitle(), getDescription(), getStartTime(), getEndTime(),getLabels(),new TripType(getTripType()), p);
        loginBean.getUser().addTrip(trip);
        if (tripService.addTrip(trip)) {
            currentTrip = trip;
            clearFields();
            return SUCCESS;
        } else {
            return FAILURE;
        }
    }

    public void clearFields(){
          title=null;
        description=null;
        isPublic=null;
        tripType=null;
        startTime=null;
        endTime=null;
        label=null;
        labels=null;
    }
}
