package be.kdg.groeph.bean;

import be.kdg.groeph.model.Trip;
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
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Component
@SessionScoped
@Named
public class WaypointBean  implements Serializable {
    static Logger logger = Logger.getLogger(WaypointBean.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";
    private static final String WAYPOINT = "WAYPOINT";

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
    @NotNull(message = "{coordinates} {notempty}")
    private double lattitude;
    @NotNull(message = "{coordinates} {notempty}")
    private double longitude;

    private Waypoint currentWaypoint;

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

    public List<Waypoint> getTripWaypoints() {
        return tripBean.getCurrentTrip().getWaypoints();
    }

    public void setCurrentWaypoint(Waypoint waypoint){
        this.currentWaypoint = waypoint;
    }

    public Waypoint getCurrentWaypoint() {
        return currentWaypoint;
    }

    public String setThisAsCurrentWaypoint() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String waypointId = request.getParameter("currentWaypointId");
        Waypoint waypoint = waypointService.getWaypointById(Integer.parseInt(waypointId));
        setCurrentWaypoint(waypoint);
        return WAYPOINT;
    }


}
