package be.kdg.groeph.bean;

import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;
import be.kdg.groeph.service.WaypointService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Component
@SessionScoped
@Named
public class WaypointBean  implements Serializable {
    static Logger logger = Logger.getLogger(WaypointBean.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    @ManagedProperty(value = "#{waypointService}")
    @Autowired
    WaypointService waypointService;

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;

    @NotEmpty(message = "{description} {notempty}")
    private String description;
    @NotEmpty(message = "{label} {notempty}")
    private String label;
    @NotEmpty(message = "{waypointType} {notempty}")
    private String waypointType;
    @NotEmpty(message = "{coordinates} {notempty}")
    private double lattitude;
    @NotEmpty(message = "{coordinates} {notempty}")
    private double longitude;

    Waypoint currentWaypoint;

    public double getLattitude() {
        return lattitude;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getWaypointType() {
        return waypointType;
    }

    public void setWaypointType(String waypointType) {
        this.waypointType = waypointType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }



    public String addWaypoint() {
        WaypointType type = waypointService.getTypeByName(getWaypointType());
        Waypoint waypoint= new Waypoint(getLabel(),getDescription(),type,getLattitude(),getLongitude());
        tripBean.getCurrentTrip().addWaypoint(waypoint);

        if(waypointService.addWaypoint(waypoint) )
        {
            clearfield();
            return SUCCESS;
        }
        return FAILURE  ;
    }

    private void clearfield() {
            label=null;
            description=null;
            lattitude=0;
            longitude=0;
            waypointType=null;
    }

    public List<WaypointType> getAllWaypointTypes(){
        return waypointService.fetchAllWaypointTypes();
    }
}
