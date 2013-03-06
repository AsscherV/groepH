package be.kdg.groeph.mockMother;

import be.kdg.groeph.bean.RegisterBean;
import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.util.SHAEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.keygen.StringKeyGenerator;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.Date;


@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class UserMother {
    private static final String USER = "ROLE_USER";
    private static final String ADMIN = "ROLE_ADMIN";

    @Qualifier("registerBean")
    @Autowired
    RegisterBean registerBean;


    public static TripUser validUser1() {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.FEBRUARY, 10);
        Address address = new Address("Baanhof", "76", "2330", "Merksplas");
        return new TripUser("Gunther", "Laurijssens", cal.getTime(), "0498216718", 'M', "gunther.laurijssens@student.kdg.be", SHAEncryption.encrypt("password"), address, new Date(), USER);
    }

    public static TripUser validUser2() {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address = new Address("TestStreet", "TestStreetNumber", "TestZipcode", "TestCity");
        return new TripUser("Greg", "Deckers", cal.getTime(), "0477879057", 'M', "greg.deckers@student.kdg.be", SHAEncryption.encrypt("password"), address, new Date(), USER);
    }

    public static TripUser validUser3WithOutEncryptedPassword() {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address = new Address("TestStreet", "TestStreetNumber", "TestZipcode", "TestCity");
        return new TripUser("GroepH", "Test", cal.getTime(), "0477879057", 'M', "groephtest@outlook.com", "password", address, new Date(), USER);
    }

    public static TripUser invalidEmailUser() {

        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address = new Address("TestStreet", "TestStreetNumber", "TestZipcode", "TestCity");
        boolean isAdmin = false;
        return new TripUser("Greg", "Deckers", cal.getTime(), "0477879057", 'M', "", SHAEncryption.encrypt("password"), address, new Date(), USER);
    }

    public static TripUser nonEE() {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address = new Address("TestStreet", "TestStreetNumber", "TestZipcode", "TestCity");
        return new TripUser("Greg", "Deckers", cal.getTime(), "0477879057", 'M', "greg.deckers@student.kdg.be", SHAEncryption.encrypt("password"), address, new Date(), USER);
    }

}
