package be.kdg.groeph.model;


import be.kdg.groeph.model.Null.Nullable;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_waypoint")
public class Waypoint implements Nullable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "label", nullable = false, length = 100)
    private String label;
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Column(name = "lattitude", nullable = false)
    private double lattitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;
    @Column(name = "correctAnswer", nullable = true)
    private Integer correctAnswer;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "waypoint")     //,fetch = FetchType.EAGER
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private List<Answer> answers;

    @ManyToOne
    @JoinColumn(name = "waypointType", nullable = true)
    private WaypointType waypointType;

    @ManyToOne
    @JoinColumn(name = "trip", nullable = false)
    //@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private Trip trip;


    public Waypoint() {

    }

    public Waypoint(String label, String description, WaypointType waypointType, double lattitude, double longitude) {
        this.label = label;
        this.description = description;
        this.waypointType = waypointType;
        this.lattitude = lattitude;
        this.longitude = longitude;
        answers = new ArrayList<Answer>();

    }

    public Waypoint(String label, WaypointType waypointType, double lattitude, double longitude, String question, String answer1, String answer2, String answer3, String answer4, int correctAnswer) {
        this.label = label;
        this.waypointType = waypointType;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.description = question;
        this.correctAnswer = correctAnswer;
        answers = new ArrayList<Answer>();
        Answer a1 = new Answer(answer1);
        a1.setWaypoint(this);
        Answer a2 = new Answer(answer2);
        a2.setWaypoint(this);
        Answer a3 = new Answer(answer3);
        a3.setWaypoint(this);
        Answer a4 = new Answer(answer4);
        a4.setWaypoint(this);
        this.answers.add(a1);
        this.answers.add(a2);
        this.answers.add(a3);
        this.answers.add(a4);

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


    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Waypoint waypoint = (Waypoint) o;

        if (id != waypoint.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
