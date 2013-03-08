package be.kdg.groeph.bean;

import be.kdg.groeph.model.Accessory;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.AccessoryService;
import be.kdg.groeph.service.TripService;
import be.kdg.groeph.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

@Component
@Named
@RequestScoped
public class AccessoryBean implements Serializable {

    private String description;
    private TripUser user;
    private String userLastname;
    private Accessory currentAccessory;
    private boolean editableAccessory;
    private String newdescription;
    private Map<Accessory, Boolean> editableAccessories;

    public AccessoryBean() {
        editableAccessories = new HashMap<Accessory, Boolean>() ;
    }

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;

    @ManagedProperty(value = "#{accessoryService}")
    @Autowired
    AccessoryService accessoryService;

    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;

    public void setDescription(String description) {
        this.description = description;
    }

    public String addAccessory() {
        if (!description.equals("")) {

            Accessory accessory = new Accessory(description, new ArrayList<TripUser>());
            tripBean.getCurrentTrip().addAccessory(accessory);
            setCurrentAccessory(accessory);

            if (accessoryService.addAccessory(accessory)) {
                description = "";
                return Tools.SUCCESS;
            }
        }
        return Tools.FAILURE;

    }

    public void addUser() {
        setCurrentAccessoryFromRequest();
        currentAccessory.addTripUser(user);
        user.addAccessory(currentAccessory);
        accessoryService.updateAccessory(currentAccessory);

        tripBean.refreshCurrentTrip();
    }

    public String editAccessory() {
        setCurrentAccessoryFromRequest();
        for (Accessory acc :  tripBean.getCurrentTrip().getAccessories()) {
                    if (acc == currentAccessory) {
                        newdescription = currentAccessory.getDescription();
                        editableAccessories.put(acc, true);
                    }
                }

        return null;
    }

    public Boolean getEditable(Accessory accessory) {
            return editableAccessories.get(currentAccessory);
        }

    public String updateAccessory() {
        setCurrentAccessoryFromRequest();
        currentAccessory.setDescription(newdescription);
        editableAccessory = false;
        if (accessoryService.updateAccessory(currentAccessory)) {
            clearfield();
            return Tools.SUCCESS;
        }
        return Tools.FAILURE;
    }

    public String cancel() {
        editableAccessory = true;
        return "CANCEL";
    }

    public String deleteAccessory() {
        setCurrentAccessoryFromRequest();

        tripBean.getCurrentTrip().deleteAccessory(currentAccessory);
        if (accessoryService.deleteAccessory(currentAccessory)) {

            return Tools.SUCCESS;
        }
        return Tools.FAILURE;
    }
    private void setCurrentAccessoryFromRequest() {
        if(FacesContext.getCurrentInstance() != null ) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                          String accessoryId = request.getParameter("accessoryId");
                          Accessory accessory = accessoryService.getAccessoryById(Integer.parseInt(accessoryId));

                          setCurrentAccessory(accessory);
        }
        }
    private void clearfield() {
        description = null;
    }

    public String getDescription() {
        return description;
    }

    public TripUser getUser() {
        return user;
    }

    public void setUser(TripUser user) {
        this.user = user;
    }

    public Accessory getCurrentAccessory() {
        return currentAccessory;
    }

    public void setCurrentAccessory(Accessory currentAccessory) {
        this.currentAccessory = currentAccessory;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        setUserByLastName(userLastname);
        this.userLastname = userLastname;
    }

    private void setUserByLastName(String userLastname) {
        for (TripUser user : tripBean.getCurrentTrip().getConfirmedTripUsers()) {
            if (user.getLastName().equals(userLastname)) {
                this.user = user;
            }
        }
    }

    public boolean isEditableAccessory() {
        return editableAccessory;
    }

    public void setEditableAccessory(boolean editableAccessory) {
        this.editableAccessory = editableAccessory;
    }

    public String getNewdescription() {
        return newdescription;
    }

    public void setNewdescription(String newdescription) {
        this.newdescription = newdescription;
    }
}