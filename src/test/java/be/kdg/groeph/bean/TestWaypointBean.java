package be.kdg.groeph.bean;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.dao.WaypointDao;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestWaypointBean extends AbstractTransactionalJUnit4SpringContextTests {
    public static final String SUCCESS = "SUCCESS";
    public static final String START = "Start";
    @Qualifier("waypointBean")
    @Autowired
    WaypointBean waypointBean;
    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;
    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;
    @Autowired
    private TripDao tripDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private WaypointDao waypointDao;

    private TripUser user1;
    private Trip trip;

    @Before
    public void init() {
        user1 = UserMother.validUser1();
        loginBean.setUser(UserMother.validUser1());
        tripDao.addTripType(new TripType("Timebound"));
        tripDao.addTripType(new TripType("Public"));
        tripDao.addTripType(new TripType("Private"));
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setTitle("Lekker int antwaarps");
        tripBean.setDescription("Een publieke test trip");
        tripBean.setStartTime(cal.getTime());
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setEndTime(cal.getTime());
        ArrayList<Label> lbls = new ArrayList<Label>();
        tripBean.setLabel("Test");
        lbls.add(new Label("Test"));
        lbls.add(new Label("Test1"));
        lbls.add(new Label("Test2"));
        lbls.add(new Label("Test3"));
        lbls.add(new Label("Test4"));
        tripBean.setLabels(lbls);
        tripBean.setTripType("Timebound");
        tripBean.setPublic(true);
        tripBean.addTrip();

        waypointDao.addWaypointType(new WaypointType("Start"));
        waypointDao.addWaypointType(new WaypointType("End"));
        waypointDao.addWaypointType(new WaypointType("Overnight"));
        waypointDao.addWaypointType(new WaypointType("Meeting"));
        TripUser usr = userDao.getUserByEmail(user1.getEmail());
        trip = usr.getTrips().get(0);
    }

    @Test
    public void testAddWaypointTrip() {
        TripUser daoTestUser = null;
        Trip trip = null;
        waypointBean.setLabel(START);
        waypointBean.setDescription("This is where we meet before starting the trip");
        waypointBean.setWaypointType("Start");
        waypointBean.setLattitude(50.9);
        waypointBean.setLongitude(4.3);
        waypointBean.addWaypoint();
        daoTestUser = userDao.getUserByEmail(loginBean.getUser().getEmail());

        trip = daoTestUser.getTrips().get(0);
        assertEquals("The trip must contain 1 waypoint", 1, trip.getWaypoints().size());
        assertEquals("The waypoint must have Start as label", START, trip.getWaypoints().get(0).getLabel());
    }

}
