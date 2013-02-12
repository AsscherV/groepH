package be.kdg.groeph.mockMother;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.util.SHAEncryption;

import java.util.Calendar;
import java.util.Date;

/**
 * To change this template use File | Settings | File Templates.
 */
public class UserMother {
    public static TripUser validUser1(){
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.FEBRUARY, 10);
        Address address=new Address("Baanhof","76","2330","Merksplas");
        boolean isAdmin = false;
        return new TripUser("Gunther", "Laurijssens", cal.getTime(),"0498216718", 'M',"gunther.laurijssens@student.kdg.be",SHAEncryption.encrypt("password"),address, new Date(), "ROLE_USER", isAdmin);
    }

    public static TripUser validUser2(){
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address=new Address("TestStreet","TestStreetNumber","TestZipcode","TestCity");
        boolean isAdmin = false;
        return new TripUser("Greg", "Deckers", cal.getTime(),"0477879057", 'M',"greg.deckers@student.kdg.be", SHAEncryption.encrypt("password"), address, new Date(), "ROLE_USER", isAdmin);
    }

    public static TripUser invalidEmailUser(){

        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address=new Address("TestStreet","TestStreetNumber","TestZipcode","TestCity");
        boolean  isAdmin = false;
        return new TripUser("Greg", "Deckers", cal.getTime(),"0477879057", 'M',"",SHAEncryption.encrypt("password"),address, new Date(), "ROLE_USER", isAdmin);
    }

}
