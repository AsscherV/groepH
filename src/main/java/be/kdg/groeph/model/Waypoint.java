package be.kdg.groeph.model;


import be.kdg.groeph.model.Null.Nullable;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="t_waypoint")
public class Waypoint implements Nullable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="label", nullable = false, length = 100)
    private String label;
    @Column(name="description", nullable = false, length = 200)
    private String description;
    @Column(name="lattitude", nullable = false)
    private double lattitude;
    @Column(name="longitude", nullable = false)
    private double longitude;

    @ManyToOne
    @JoinColumn(name = "waypointType", nullable = true)
    private WaypointType waypointType;

    @ManyToOne
    @JoinColumn(name = "trip", nullable = false)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Trip trip;

    public Waypoint(){

    }

    public Waypoint(String label, String description, WaypointType waypointType, double lattitude, double longitude) {

        this.label=label;
        this.description=description;
        this.waypointType=waypointType;
        this.lattitude=lattitude;
        this.longitude=longitude;

    }

    public WaypointType getWaypointType() {
        return waypointType;
    }

    public void setWaypointType(WaypointType waypointType) {
        this.waypointType = waypointType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }


    @Override
    public boolean isNull() {
        return false;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
