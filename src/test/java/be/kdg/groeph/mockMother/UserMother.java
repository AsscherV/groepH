package be.kdg.groeph.mockMother;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.User;

import java.util.Calendar;
import java.util.Date;

/**
 * To change this template use File | Settings | File Templates.
 */
public class UserMother {
    public static User validUser1(){
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.FEBRUARY, 10);
        Address address=new Address("Baanhof","76","2330","Merksplas");
        return new User("Gunther", "Laurijssens", cal.getTime(),"0498216718", 'M',"gunther.laurijssens@student.kdg.be","wachtwoord",address, new Date(), "User");
    }

    public static User validUser2(){
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address=new Address("TestStreet","TestStreetNumber","TestZipcode","TestCity");
        return new User("Greg", "Deckers", cal.getTime(),"0477879057", 'M',"greg.deckers@student.kdg.be","wachtwoord",address, new Date(), "User");
    }

}
