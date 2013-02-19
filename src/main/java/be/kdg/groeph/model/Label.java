package be.kdg.groeph.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="t_label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "trip", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Trip trip;

    public Label() {
    }

    public Label(String label) {
        this.name=label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
