package be.kdg.groeph.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "t_accessory")
public class Accessory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "description", nullable = false, length = 100)
    private String description;
    @Column(name = "isChecked", nullable = true)
    private boolean isChecked;

    @LazyCollection(LazyCollectionOption.FALSE)
    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(name = "t_accessory_t_tripuser", joinColumns = {
            @JoinColumn(name = "accessoryId", nullable = true, updatable = true)},
            inverseJoinColumns = {@JoinColumn(name = "tripUserId",
                    nullable = true, updatable = true)})
    private List<TripUser> tripUsers;
    @ManyToOne
    @JoinColumn(name = "trip", nullable = false)
    //@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Trip trip;



    public Accessory() {
        tripUsers = new ArrayList<TripUser>() ;
    }

    public Accessory(String description, List<TripUser> tripUsers) {
        this.description=description;
        this.tripUsers=tripUsers;

    }

    public Trip getTrip() {

        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TripUser> getTripUsers() {
        return tripUsers;
    }
    public boolean getTripUsersIsEmpty() {
        if(tripUsers.isEmpty()){
            return true;
        } else{
            return false;
        }

       }

    public void setTripUsers(List<TripUser> tripUsers) {
        this.tripUsers = tripUsers;
    }

    public void addTripUser(TripUser user){
        this.tripUsers.add(user);

    }
    public void removeTripUser(TripUser user){
            this.tripUsers.remove(user);

        }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
