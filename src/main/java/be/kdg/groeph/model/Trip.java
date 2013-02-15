package be.kdg.groeph.model;

import be.kdg.groeph.model.Null.NullTrip;
import be.kdg.groeph.model.Null.NullUser;
import be.kdg.groeph.model.Null.Nullable;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Column(name = "startTime", nullable = false)
    private Date startTime;
    @Column(name = "endTime", nullable = false)
    private Date endTime;
    @Column(name = "isPubic", nullable = false)
    private boolean isPublic;

    @OneToMany(mappedBy = "trip")
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Label> labels = new ArrayList<Label>();

    @ManyToOne
    @JoinColumn(name = "tripType", nullable = true)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private TripType tripType;


    public Trip() {
    }

    public Trip(String title, String description, Date startTime, Date endTime, ArrayList<Label> labels, TripType tripType, boolean isPublic) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.labels = labels;
        this.tripType = tripType;
        this.isPublic = isPublic;
    }

    public void addLabel(Label label) {
        label.setTrip(this);
        labels.add(label);
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public TripType getTripType() {
        return tripType;
    }

    public void setTripType(TripType tripType) {
        this.tripType = tripType;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    public static Trip INVALID_TRIP() {
        return new NullTrip();
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
