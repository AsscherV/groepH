package be.kdg.groeph.bean;

import be.kdg.groeph.model.Accessory;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.AccessoryService;
import be.kdg.groeph.service.TripService;
import be.kdg.groeph.util.Tools;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Named
@RequestScoped
public class AccessoryBean implements Serializable {
    static Logger logger = Logger.getLogger(AccessoryBean.class);

    private String description;
    private TripUser user;
    private String userLastname;
    private Accessory currentAccessory;
    private boolean editableAccessory;
    private String newdescription;
    private Map<Integer, Boolean> editableAccessories;
    private Map<Integer, Boolean> addableAccessories;
    private ArrayList<TripUser> userlist;

    public AccessoryBean() {
        editableAccessories = new HashMap<Integer, Boolean>();
        addableAccessories = new HashMap<Integer, Boolean>();
        userlist = new ArrayList<TripUser>();
    }

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @ManagedProperty(value = "#{accessoryService}")
    @Autowired
    AccessoryService accessoryService;

    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;

    public String addAccessory() {
        try {
            if (!description.equals("")) {
                Accessory accessory = new Accessory(description, new ArrayList<TripUser>());
                tripBean.getCurrentTrip().addAccessory(accessory);
                setCurrentAccessory(accessory);

                if (accessoryService.addAccessory(currentAccessory)) {
                    description = "";
                    editableAccessories.clear();
                    logger.info(accessory.getDescription() + "added.");
                    return Tools.SUCCESS;
                }
            }
            return Tools.FAILURE;
        } catch (NullPointerException e) {
            logger.error("Failed to add accessory " + e.toString());
            return Tools.FAILURE;
        }
    }

    //Addable users = Users that haven't got the accesory yet.
    public List<TripUser> addableConfirmedTripUsers(Accessory accessory) {
        try {
            userlist.clear();
            for (TripUser user : tripBean.currentTrip.getConfirmedTripUsers()) {
                if (!accessory.getTripUsers().contains(user)) {
                    userlist.add(user);
                }
            }
            return userlist;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return null;
        }
    }

    public boolean hasAddableTripUsers(Accessory accessory) {
        try {
            if (addableConfirmedTripUsers(accessory).size() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (NullPointerException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public void addUserToAccessory() {
        try {
            currentAccessory.addTripUser(user);
            user.addAccessory(currentAccessory);
            addableAccessories.clear();
            accessoryService.updateAccessory(currentAccessory);
            logger.info("User: " + user.getEmail() + " added to accessory: " + currentAccessory.getDescription());
        } catch (NullPointerException e) {
            logger.error(e.toString());
        }
    }

    public void removeUserFromAccessory(Accessory accessory) {
        try {
            setCurrentAccessory(accessory);
            currentAccessory.removeTripUser(user);
            accessoryService.updateAccessory(currentAccessory);
            logger.info("User: " + user.getEmail() + " removed from accessory: " + accessory.getDescription());
        } catch (NullPointerException e) {
            logger.error(e.toString());
        }

    }

    public String editAccessory(Accessory accessory) {
        try {
            setCurrentAccessory(accessory);
            newdescription = currentAccessory.getDescription();
            editableAccessories.put(currentAccessory.getId(), true);

            return null;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return null;
        }
    }

    public String changeAddUser(Accessory accessory) {
        try {
            setCurrentAccessory(accessory);
            addableAccessories.put(currentAccessory.getId(), true);
            return null;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return null;
        }
    }

    public String updateAccessory() {
        try {

            editableAccessories.clear();
            addableAccessories.clear();
            currentAccessory.setDescription(newdescription);
            if (accessoryService.updateAccessory(currentAccessory)) {
                description = null;
                logger.info("Accessory: " + currentAccessory.getDescription() + " changed");
                return Tools.SUCCESS;
            }
            logger.info("Accessory: " + currentAccessory.getDescription() + " could not be changed");
            return Tools.FAILURE;

        } catch (NullPointerException e) {
            logger.error(e.toString());
            return Tools.FAILURE;
        }
    }

    public String cancel(Accessory accessory) {
        try {
            setCurrentAccessory(accessory);
            newdescription = "";
            editableAccessories.clear();
            addableAccessories.clear();
            return null;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return null;
        }
    }

    public String deleteAccessory(Accessory accessory) {
        try {
            setCurrentAccessory(accessory);
            currentAccessory.getTripUsers().clear();
            editableAccessories.clear();
            tripBean.getCurrentTrip().deleteAccessory(currentAccessory);
            if (accessoryService.deleteAccessory(currentAccessory)) {
                logger.info("Accessory: " + accessory.getDescription() + " removed");
                return Tools.SUCCESS;
            }
            return Tools.FAILURE;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return Tools.FAILURE;
        }
    }

    public void updateChecked(Accessory accessory) {
        try {
            if (accessory.isChecked()) {
                accessory.setChecked(false);
            } else {
                accessory.setChecked(true);
            }
            accessoryService.updateAccessory(accessory);
            logger.info("Accessory: " + accessory.getDescription() + " checked changed");
        } catch (NullPointerException e) {
            logger.error(e.toString());
        }
    }

    public boolean isAddedConfirmedUser(Accessory accessory) {
        try {
            if (accessory.getTripUsers().contains(loginBean.getUser())) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public boolean isOrganizer() {
        try {
            if (loginBean.getUser().getId() == tripBean.getCurrentTrip().getTripUser().getId()) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public Boolean getEditable(Accessory accessory) {
        try {
            return editableAccessories.get(accessory.getId());
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public Boolean getAdable(Accessory accessory) {
        try {
            return addableAccessories.get(accessory.getId());
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public boolean getContainsEditable() {
        try {
            return editableAccessories.containsValue(true);
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public List<Accessory> getTripaccessories() {
        try {
            return accessoryService.getAccessoriesByTrip(tripBean.getCurrentTrip());
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return null;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        try {
            for (TripUser user : tripBean.getCurrentTrip().getConfirmedTripUsers()) {
                if (user.getLastName().equals(userLastname)) {
                    this.user = user;
                }
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
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