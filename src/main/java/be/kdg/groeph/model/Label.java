package be.kdg.groeph.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 7/02/13
 * Time: 15:34
 */
@Entity
@Table(name="t_label")
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="name", nullable = false, length = 100)
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_trip_label", joinColumns = {@JoinColumn(name = "trips")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    @Column(name = "trips", nullable = true)
    private List<Trip> trips;

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
}
