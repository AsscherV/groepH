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
import javax.inject.Named;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Named
@RequestScoped
public class AccessoryBean implements Serializable{

    private String description;
    private Set<TripUser> tripUsers;

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

    public String getDescription() {
        return description;
    }

    public String AddAccessory() {

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
}