package be.kdg.groeph.bean;

import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.service.TripService;
import be.kdg.groeph.service.TripServiceImpl;
import be.kdg.groeph.util.TripType;
import org.apache.log4j.Logger;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Date: 7/02/13
 * Time: 15:05
 */
@Component
@ViewAccessScoped
@Named
@ManagedBean(name="tripBean")
public class TripBean implements Serializable {
    static Logger logger = Logger.getLogger(TripBean.class);
    @ManagedProperty(value="#{tripService}")
    @Autowired
    TripService tripService;

    private String title;
    private Set<Label> labels;
    private TripType type;
    private String description;
    private Date startTime;
    private boolean isPublic;

    public TripBean() {
        labels=new HashSet<Label>();
    }

    public String addTrip(){
        Trip trip = new Trip(title,description,labels,startTime,isPublic);
        return tripService.addTrip(trip)? "SUCCESS":"FAILURE";
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

    public void setType(TripType type) {
        this.type = type;
    }

    public TripType getType() {
        return type;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setStartTime(Date time) {
           this.startTime =time;

    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
