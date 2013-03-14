package be.kdg.groeph.model;

import be.kdg.groeph.model.Null.Nullable;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "t_tripType")
public class TripType implements Serializable, Nullable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @OneToMany(mappedBy = "tripType")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Trip> trips = new ArrayList<Trip>();

    public TripType() {

    }

    public TripType(String type) {
        this.type = type;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void addTrip(Trip trip) {
        trip.setTripType(this);
        trips.add(trip);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TripType tripType = (TripType) o;

        return !(type != null ? !type.equals(tripType.type) : tripType.type != null);
    }
}
