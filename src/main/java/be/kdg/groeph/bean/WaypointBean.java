package be.kdg.groeph.bean;

import be.kdg.groeph.model.Waypoint;
import be.kdg.groeph.model.WaypointType;
import be.kdg.groeph.service.WaypointService;
import be.kdg.groeph.util.Tools;
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
public class WaypointBean implements Serializable {
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

    @NotEmpty(message = "{description} {notempty}")
    private String newdescription;
    @NotEmpty(message = "{label} {notempty}")
    private String newlabel;
    @NotNull(message = "{coordinates} {notempty}")
    private double newlattitude;
    @NotNull(message = "{coordinates} {notempty}")
    private double newlongitude;

    private Waypoint currentWaypoint;
    private List<Waypoint> waypointList;

    private boolean editableWaypoint;
    private boolean isInteractive;
    @NotEmpty(message = "{correctAnswer} {notempty}")
    private Integer correctAnswer;
    private String positions;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private boolean visible;


    public WaypointBean() {
        editableWaypoint = false;

    }
    public List<Waypoint> getWaypointList() {
        return waypointList;
    }

    public void setWaypointList(List<Waypoint> waypointList) {
        this.waypointList = waypointList;
    }

    public boolean isInteractive() {
        return isInteractive;
    }

    public void setInteractive(boolean interactive) {
        isInteractive = interactive;
    }
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

    public String getNewdescription() {
        return newdescription;
    }

    public void setNewdescription(String newdescription) {
        this.newdescription = newdescription;
    }

    public String getNewlabel() {
        return newlabel;
    }

    public void setNewlabel(String newlabel) {
        this.newlabel = newlabel;
    }


    public double getNewlattitude() {
        return newlattitude;
    }

    public void setNewlattitude(double newlattitude) {
        this.newlattitude = newlattitude;
    }

    public double getNewlongitude() {
        return newlongitude;
    }

    public void setNewlongitude(double newlongitude) {
        this.newlongitude = newlongitude;
    }

    public boolean isEditableWaypoint() {
        return editableWaypoint;
    }

    public void setEditableWaypoint(boolean editableWaypoint) {
        this.editableWaypoint = editableWaypoint;
    }

    public String addWaypoint() {
        Waypoint waypoint;
        WaypointType type = waypointService.getTypeByName(getWaypointType());
        if(isInteractive)
        {
            waypoint= new Waypoint(getLabel(),type,getLattitude(),getLongitude(),getDescription(),getAnswer1(),getAnswer2(),getAnswer3(),getAnswer4(),getCorrectAnswer());
        }
        else
        {
            waypoint = new Waypoint(getLabel(), getDescription(), type, getLattitude(), getLongitude());

        }
        tripBean.getCurrentTrip().addWaypoint(waypoint);
        setCurrentWaypoint(waypoint);

        if (waypointService.addWaypoint(waypoint)) {
            clearfield();
            return SUCCESS;
        }
        return FAILURE;
    }

    public String editWaypoint() {
        newlabel = currentWaypoint.getLabel();
        newdescription = currentWaypoint.getDescription();
        newlattitude = currentWaypoint.getLattitude();
        newlongitude = currentWaypoint.getLongitude();
        waypointType = currentWaypoint.getWaypointType().getType();

        editableWaypoint = true;
        return "EDITWAYPOINT";
    }

    public String updateWaypoint() {
        WaypointType newtype = waypointService.getTypeByName(getWaypointType());

        Waypoint waypoint = getCurrentWaypoint();
        waypoint.setLabel(newlabel);
        waypoint.setDescription(newdescription);
        waypoint.setWaypointType(newtype);
        waypoint.setLattitude(newlattitude);
        waypoint.setLongitude(newlongitude);
        editableWaypoint = false;
        if (waypointService.updateWaypoint(waypoint)) {
            clearfield();
            return Tools.SUCCESS;
        }
        return Tools.FAILURE  ;
    }

    public String cancel(){
        editableWaypoint = true;
        return "CANCEL";
    }
    public String deleteWaypoint() {
        Waypoint waypoint = getCurrentWaypoint();
        tripBean.getCurrentTrip().deleteWaypoint(waypoint);
        System.out.println("id: "+waypoint.getId());

        if (waypointService.deleteWaypoint(waypoint)) {
            return Tools.SUCCESS;
        }
        return Tools.FAILURE;
    }


    public String previousWaypoint() {
        waypointList = getTripWaypoints();

        if (waypointList.get(0).equals(currentWaypoint)) {
            currentWaypoint = waypointList.get(waypointList.size() -1);
        } else {
            currentWaypoint = waypointList.get(waypointList.indexOf(currentWaypoint) -1);
        }
        return null;
    }

    public String nextWaypoint() {
        waypointList = getTripWaypoints();

        if (waypointList.get(waypointList.size() -1).equals(currentWaypoint)) {
            currentWaypoint = waypointList.get(0);
        } else {
            currentWaypoint = waypointList.get(waypointList.indexOf(currentWaypoint) + 1);
        }
        return null;
    }

    private void clearfield() {
        label = null;
        description = null;
        lattitude = 0;
        longitude = 0;
        waypointType = null;
    }

    public List<WaypointType> getAllWaypointTypes() {
        return waypointService.fetchAllWaypointTypes();
    }

    public List<Waypoint> getTripWaypoints() {
        return waypointService.getWaypointsByTrip(tripBean.getCurrentTrip());
    }

    public void setCurrentWaypoint(Waypoint waypoint) {
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

    public String getPositions() {
        positions = "";
        for(Waypoint wp:getTripWaypoints()){
            positions += wp.getLattitude() + " ";
            positions += wp.getLongitude() + " ";
        }
        return positions.trim();
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }


    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getAnswer4() {
        return answer4;
    }
}
