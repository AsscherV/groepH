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
    @Column(name="number", nullable = true, length = 20)
    private String number;
    @Column(name="zipcode", nullable = true, length = 20)
    private String zipcode;
    @Column(name="city", nullable = true, length = 50)
    private String city;


    public Address() {
    }

    public Address(String street, String number, String zipcode, String city) {
        this.street = street;
        this.number = number;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
        sb.append(street);
        sb.append(" ");
        sb.append(number);
        sb.append(" ");
        sb.append(zipcode);
        sb.append(" ");
        sb.append(city);
        return sb.toString();
    }
}


