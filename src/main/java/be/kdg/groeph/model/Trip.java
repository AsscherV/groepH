package be.kdg.groeph.model;

import be.kdg.groeph.model.Null.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 7/02/13
 * Time: 15:19
 */
@Entity
@Table(name = "t_trip")
public class Trip implements Nullable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Column(name = "description", nullable = true, length = 200)
    private String description;
    @Column(name = "isPublic", nullable = false)
    private Boolean isPublic;
    //TODO: ManyToMany Nakijken!!
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "t_trip_label", joinColumns = {@JoinColumn(name = "labels")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    @Column(name = "labels", nullable = true)
    private Set<Label> labels;

    public Trip(String title, String description, Set<Label> labels, Date startTime, boolean aPublic) {
       this.title=title;
       this.description=description;

    }

    public String getTitle() {
        return title;
    }



    @Override
    public boolean isNull() {
        return false;
    }

}
