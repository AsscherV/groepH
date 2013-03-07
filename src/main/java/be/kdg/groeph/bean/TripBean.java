package be.kdg.groeph.bean;

import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.TripService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<Label> labels;
    @NotEmpty(message = "{tripType} {notempty}")
    private String tripType;
    private boolean isPublic;
    Trip currentTrip;
    private String filter;
    private boolean isVisible;
    private boolean hasCurrentTrip;

    public TripBean() {
        isPublic = true;
        labels = new ArrayList<Label>();
        filter = "";
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public boolean getPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
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

    public List<Label> getLabels() {

        labels = tripService.getLabels(getCurrentTrip());

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

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isHasCurrentTrip() {
        if(currentTrip == null){
            hasCurrentTrip= false;
        }
        else{
            hasCurrentTrip= true;
        }
        return hasCurrentTrip;
    }

    public void setHasCurrentTrip(boolean hasCurrentTrip) {
        this.hasCurrentTrip = hasCurrentTrip;
    }
    public String NoCurrentTrip(){
        setCurrentTrip(null);
        return null;
    }
    public String setThisAsCurrentTrip() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Integer tripId = Integer.parseInt(request.getParameter("currentTrip"));
        Trip trip = tripService.getTripById(tripId); //tripService.getTripByName(tripName);

        setCurrentTrip(trip);
        return SUCCESS;
    }

    public String addTrip() {
        setVisible(false);
        TripType type = tripService.getTypeByName(getTripType());
        Trip trip = new Trip(getTitle(), getDescription(), getStartTime(), getEndTime(), type, getPublic(), isVisible);
        Label label = new Label(getLabel());
        trip.addLabel(label);

        loginBean.getUser().addTrip(trip);
        if (tripService.addTrip(trip)) {
            currentTrip = trip;
            clearFields();
            return SUCCESS;
        } else {
            return FAILURE;
        }
    }

    public void clearFields() {
        title = null;
        description = null;
        isPublic = true;
        tripType = null;
        startTime = null;
        endTime = null;
        label = null;
        labels = null;
    }

    public List<Trip> getAllPublicTrips() {
        List<Trip> publictrips = tripService.fetchAllPublicTrips();
        return Tools.filter(publictrips, filter);
    }

    public List<TripType> getAllTripTypes() {
        return tripService.fetchAllTripTypes();
    }

    public List<Trip> getAllInvitedTrips() {
        return tripService.getAllInvitedTripsByUser(loginBean.getUser());
    }

    public List<Trip> getAllParticipatedTrips() {
        return tripService.getAllParticipatedTripsByUser(loginBean.getUser());
    }

    public List<Trip> getAllPrivateTrips() {

        return Tools.filter(loginBean.getUser().getTrips(), filter);
    }

    public void confirmParticipation() {
        TripUser user = loginBean.getUser();
        currentTrip.addConfirmedUser(user);
        user.confirmParticipation(currentTrip);
        tripService.addConfirmedTrip(currentTrip);
    }

    public boolean isConfirmInvitation() {
        if (currentTrip != null) {
            if (currentTrip.getTripUsers().contains(loginBean.getUser())) {
                return true;
            } else {
                return false;
            }
        }
        return  false;
    }

    public boolean publishTrip(){
        currentTrip.setVisible(true);
        if(tripService.updateTrip(currentTrip)){
            return true;
        }else{
            return false;
        }
    }
}