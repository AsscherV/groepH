package be.kdg.groeph.bean;

import be.kdg.groeph.model.Accessory;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.AccessoryService;
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
import java.util.HashSet;
import java.util.Set;

@Component
@Named
@RequestScoped
public class AccessoryBean implements Serializable{

    private String description;
    private Set<TripUser> tripUsers;
    private Accessory currentAccessory;
    private boolean editableAccessory;
    private String newdescription;

    public AccessoryBean() {
        tripUsers= new HashSet<TripUser>();
    }

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;

    @ManagedProperty(value = "#{accessoryService}")
    @Autowired
    AccessoryService accessoryService;

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<TripUser> getTripUsers() {
        return tripUsers;
    }

    public void setTripUsers(Set<TripUser> tripUsers) {
        this.tripUsers = tripUsers;
    }

    public Accessory getCurrentAccessory() {
        return currentAccessory;
    }

    public void setCurrentAccessory(Accessory currentAccessory) {
        this.currentAccessory = currentAccessory;
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

    public TripBean getTripBean() {
        return tripBean;
    }

    public void setTripBean(TripBean tripBean) {
        this.tripBean = tripBean;
    }

    public AccessoryService getAccessoryService() {
        return accessoryService;
    }

    public void setAccessoryService(AccessoryService accessoryService) {
        this.accessoryService = accessoryService;
    }

    public String getDescription() {
        return description;
    }

    public String addAccessory() {

        Accessory accessory= new Accessory(description,tripUsers);
        tripBean.getCurrentTrip().addAccessory(accessory);

        if(accessoryService.addAccessory(accessory) )
        {
            return Tools.SUCCESS;
        }
        return Tools.FAILURE  ;

    }

    public void addUser(TripUser tripUser) {
        this.tripUsers.add(tripUser);
    }

    public String editAccessory() {
        newdescription = currentAccessory.getDescription();

        editableAccessory = true;
        return "EDIT";
    }

    public String updateAccessory() {
        Accessory accessory = getCurrentAccessory();
        accessory.setDescription(newdescription);
        editableAccessory = false;
        if (accessoryService.updateAccessory(accessory)) {
            clearfield();
            return Tools.SUCCESS;
        }
        return Tools.FAILURE  ;
    }

    public String cancel(){
        editableAccessory = true;
        return "CANCEL";
    }
    public String deleteAccessory() {
        Accessory accessory = getCurrentAccessory();
        tripBean.getCurrentTrip().deleteAccessory(accessory);
        System.out.println("id: "+accessory.getId());
        if (accessoryService.deleteAccessory(accessory)) {

            return Tools.SUCCESS;
        }
        return Tools.FAILURE;
    }
    private void clearfield() {

        description = null;

    }
    public String setThisAsCurrentAccessory() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String accessoryId = request.getParameter("currentAccessoryId");
        Accessory accessory = accessoryService.getAccessoryById(Integer.parseInt(accessoryId));
        setCurrentAccessory(accessory);
        return "ACCESSORY";
    }
}