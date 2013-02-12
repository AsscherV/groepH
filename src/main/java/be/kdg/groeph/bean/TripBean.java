package be.kdg.groeph.bean;

import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.service.TripService;
import org.apache.log4j.Logger;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

/**
 * Date: 7/02/13
 * Time: 15:05
 */
@Component
@ViewAccessScoped
@Named
@ManagedBean(name = "tripBean")
public class TripBean implements Serializable {
    static Logger logger = Logger.getLogger(TripBean.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;

    private String title;
    private List<Label> labels;
    private String tripType;
    private String description;
    private Date startTime;
    private Date endTime;


    public TripBean() {
        labels = new ArrayList<Label>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public void addLabel(String label) {
        labels.add(new Label(label));
    }

    public void setType(String tripType) {
        this.tripType = tripType;
    }

    public String getTripType() {
        return tripType;
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

    public TripService getTripService() {
        return tripService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String addTrip() {
        Trip trip = new Trip(getTitle(), getDescription(), getStartTime(), getEndTime(), getTripType(), getLabels());
        if (tripService.addTrip(trip)) {
            return SUCCESS;
        } else {
            return FAILURE;
        }
        //return tripService.addTrip(trip) ? SUCCESS : FAILURE;
    }

}
