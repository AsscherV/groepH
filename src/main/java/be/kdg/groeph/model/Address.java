package be.kdg.groeph.model;

import be.kdg.groeph.model.Null.NullAddress;
import be.kdg.groeph.model.Null.Nullable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="t_address")
public class Address implements Nullable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="street", nullable = true, length = 100)
    private String street;
    @Column(name="streetNumber", nullable = true, length = 20)
    private String streetNumber;
    @Column(name="zipcode", nullable = true, length = 20)
    private String zipcode;
    @Column(name="city", nullable = true, length = 50)
    private String city;


    public Address() {
    }

    public Address(String street, String streetNumber, String zipcode, String city) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipcode = zipcode;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String number) {
        this.streetNumber = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zip) {
        this.zipcode = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    public static Address INVALID_ADDRESS() {
        return new NullAddress();
    }
    @Override
    public boolean equals(Object obj) {
        //todo aanpassen
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        //todo aanpassen
        return super.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getStreet());
        sb.append(" ");
        sb.append(getStreetNumber());
        sb.append(" ");
        sb.append(getZipcode());
        sb.append(" ");
        sb.append(getCity());
        return sb.toString();
    }
}


