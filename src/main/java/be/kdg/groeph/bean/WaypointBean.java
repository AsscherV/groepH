package be.kdg.groeph.bean;

import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;
import be.kdg.groeph.service.TripService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;


@Component
@SessionScoped
@Named
public class WaypointBean  implements Serializable {
    static Logger logger = Logger.getLogger(TripBean.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;

    @NotEmpty(message = "{title} {notempty}")
    private String title;
    @NotEmpty(message = "{description} {notempty}")
    private String description;
    @NotEmpty(message = "{label} {notempty}")
    private String label;
    @NotEmpty(message = "{waypointType} {notempty}")
    private WaypointType waypointType;
    @NotEmpty(message = "{lat} {notempty}")
    private double lat;
    @NotEmpty(message = "{lng} {notempty}")
    private double lng;

    Waypoint currentWaypoint;

    public WaypointType getWaypointType() {
        return waypointType;
    }

    public void setWaypointType(WaypointType waypointType) {
        this.waypointType = waypointType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public String addWaypoint() {
        if(tripService.addWaypoint(new Waypoint()))
        {
            return SUCCESS;
        }
        return FAILURE  ;
    }
}
