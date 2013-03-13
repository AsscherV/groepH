package be.kdg.groeph.model;

import be.kdg.groeph.model.Null.Nullable;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_repeatingTripType")
public class RepeatingTripType implements Serializable, Nullable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "repeatingType", nullable = false, length = 100)
    private String repeatingType;

    @OneToMany(mappedBy = "repeatingTripType")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Trip> trips = new ArrayList<Trip>();


    public RepeatingTripType() {

    }

    public String getRepeatingType() {
        return repeatingType;
    }

    public void setRepeatingType(String repeatingType) {
        this.repeatingType = repeatingType;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void addTrip(Trip trip){
        trip.setRepeatingTripType(this);
        trips.add(trip);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public String toString() {
        return repeatingType;
    }

}
