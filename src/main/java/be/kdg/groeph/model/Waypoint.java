package be.kdg.groeph.model;


import be.kdg.groeph.model.Null.Nullable;

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

    @Override
    public boolean isNull() {
        return false;
    }
}
