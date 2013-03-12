package be.kdg.groeph.service;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.faces.bean.ManagedProperty;
import javax.security.auth.login.LoginException;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestMailService extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String VALID_EMAIL1 = "guntherlaurijssens@gmail.com";
    private static final String NEW_EMAIL = "guntherlaurijssens@gmail.com";
    private static final String INVALID_EMAIL1 = "guntherlaurijssens";

    public static final String TIMEBOUND = "Timebound";
    public static final String REPEATING = "Repeating";
    public static final String ANYTIME = "AnyTime";
    public static final String TITLE_1 = "title1";
    private Trip trip;

    @ManagedProperty(value = "#{mailService}")
    @Autowired
    MailService mailService;

    @Autowired
    private TripDao tripDao;

    @Autowired
    private UserDao userDao;

    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;
    private List<Trip> trips;


    @Before
    public void init() throws ParseException, LoginException {
        tripDao.addTripType(new TripType(TIMEBOUND));

        TripUser tripUser1 = UserMother.validUser2();
        tripUser1.setEnabled(true);
        tripUser1.setAccountNonLocked(true);
        tripUser1.setAccountNonExpired(true);
        tripUser1.setCredentialsNonExpired(true);
        userDao.addUser(tripUser1);

        TripUser user = userDao.getUserByEmail(VALID_EMAIL1);
        makeTrips(user);
    }

    public void makeTrips(TripUser tripUser) {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        TripType type = tripDao.getTypeByName(TIMEBOUND);

        trip.setVisible(false);
        trip.setStarted(false);

        tripUser.addTrip(trip);

        tripService.addTrip(trip);
    }

    @Test
    public void testIsSuccessfulRegistration(){
        assertTrue("Returns True if email was successfully sent",mailService.isSuccessfulRegistration(VALID_EMAIL1));
        assertFalse("Returns False if email was not successfully sent",mailService.isSuccessfulRegistration(INVALID_EMAIL1));
    }
}
