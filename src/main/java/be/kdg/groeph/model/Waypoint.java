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
    @Column(name="description", nullable = false, length = 200)
    private String description;
    @Column(name="lattitude", nullable = false)
    private double lattitude;
    @Column(name="longitude", nullable = false)
    private double longitude;
    @Column(name="correctAnswer", nullable = true)
    private Integer correctAnswer;
    @Column(name="answer1", nullable = true)
    private String answer1;
    @Column(name="answer2", nullable = true)
    private String answer2;
    @Column(name="answer3", nullable = true)
    private String answer3;
    @Column(name="answer4", nullable = true)
    private String answer4;

    @ManyToOne
    @JoinColumn(name = "waypointType", nullable = true)
    private WaypointType waypointType;

    @ManyToOne
    @JoinColumn(name = "trip", nullable = false)
    //@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
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

    public Waypoint(String label, WaypointType waypointType, double lattitude, double longitude, String question,  String answer1, String answer2, String answer3, String answer4, int correctAnswer) {
        this.label=label;
        this.waypointType=waypointType;
        this.lattitude=lattitude;
        this.longitude=longitude;
        this.description=question;
        this.correctAnswer=correctAnswer;
        this.answer1=answer1;
        this.answer2=answer2;
        this.answer3=answer3;
        this.answer4=answer4;

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

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
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
