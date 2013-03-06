package be.kdg.groeph.bean;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.TripService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.faces.bean.ManagedProperty;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestTripBean extends AbstractTransactionalJUnit4SpringContextTests {
    public static final String TIMEBOUND = "Timebound";
    public static final String REPEATING = "Repeating";
    public static final String TITLE_1 = "title1";
    public static final String TEST = "TestLabel";
    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;
    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;
    @Autowired
    private TripDao tripDao;
    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;
    private Trip trip1;
    private TripUser user1;
    private List<TripType> types;
    private List<Trip> trips;

    @Before
    public void init() {
        user1 = UserMother.validUser1();
        loginBean.setUser(UserMother.validUser1());
        tripDao.addTripType(new TripType(TIMEBOUND));
        tripDao.addTripType(new TripType(REPEATING));
        tripDao.addTripType(new TripType("Anytime"));
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        TripType type = tripDao.getTypeByName(TIMEBOUND);
        List<Label> lbls= new ArrayList<Label>();
        lbls.add(new Label("Fun"));
        lbls.add(new Label(TEST));
        Trip trip = new Trip(TITLE_1, "desc", cal.getTime(), cal.getTime(), new ArrayList<Label>(), type, true);
        Trip trip2 = new Trip("titel2", "desc", cal.getTime(), cal.getTime(), new ArrayList<Label>(), type, true);
        Trip trip4 = new Trip("titel4", "desc", cal.getTime(), cal.getTime(), new ArrayList<Label>(), type, false);
        type= tripDao.getTypeByName(REPEATING);
        Trip trip3 = new Trip("titel3", "desc", cal.getTime(), cal.getTime(), lbls, type, true);

        user1.addTrip(trip);
        user1.addTrip(trip2);
        user1.addTrip(trip3);
        user1.addTrip(trip4);
        tripService.addTrip(trip);
        tripService.addTrip(trip2);
        tripService.addTrip(trip3);
        tripService.addTrip(trip4);


    }

    @Test
    public void testAddValidOpenTrip() {
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
        tripBean.setTripType(TIMEBOUND);
        tripBean.setPublic(true);
        assertEquals("addTrip result should be SUCCESS for Open Trip", "SUCCESS", tripBean.addTrip());
    }

    @Test
    public void testGetAllPublicTrips() {
        tripBean.setFilter("");
        trips= tripBean.getAllPublicTrips();
        assertEquals("getOpenTrips should give a list of 3  trips", 3, trips.size());
        assertTrue("getOpenTrips should return public trips", trips.get(0).isPublic());
    }
    @Test
    public void testFilteredPublicTripsType()
    {
        tripBean.setFilter(REPEATING);
        trips= tripBean.getAllPublicTrips();
        assertEquals("getAllPublicTrips should return 1 trip", 1, trips.size());
        assertEquals("getAllPublicTrips should return 1 trip of the type Repeating", REPEATING, trips.get(0).getTripType().getType());
    }
    @Test
    public void testFilteredPublicTripsTitle()
    {
        tripBean.setFilter(TITLE_1);
        trips= tripBean.getAllPublicTrips();
        assertEquals("getAllPublicTrips should return 1 trip",1,trips.size());
        assertEquals("getAllPublicTrips should return 1 trip with the title title1",TITLE_1,trips.get(0).getTitle());
    }
    @Test
         public void testFilteredPublicTripsLabels()
    {
        tripBean.setFilter(TEST);
        trips= tripBean.getAllPublicTrips();
        assertEquals("getAllPublicTrips should return 1 trip",1,trips.size());
        assertTrue("getAllPublicTrips should return 1 trip containing the label TestLabel",trips.get(0).getLabels().contains(new Label(TEST)));
    }
    //TODO: Testen uitwerken, trips aan loginbean.user hangen
//    @Test
//    public void testFilteredPrivateTripsType()
//    {
//        tripBean.setFilter(REPEATING);
//        trips= tripBean.getAllPrivateTrips();
//        assertEquals("getAllPrivateTrips should return 1 trip of the type Repeating", 1, trips.size());
//        assertEquals("getAllPrivateTrips should return 1 trip of the type Repeating", REPEATING, trips.get(0).getTitle());
//    }
//    @Test
//    public void testFilteredPrivateTripsTitle()
//    {
//        tripBean.setFilter(REPEATING);
//        trips= tripBean.getAllPrivateTrips();
//        assertEquals("getAllPrivateTrips should return 1 trip of the type Repeating", 1, trips.size());
//        assertEquals("getAllPrivateTrips should return 1 trip of the type Repeating", REPEATING, trips.get(0).getTitle());
//    }
//    @Test
//    public void testFilteredPrivateTripsLabels()
//    {
//        tripBean.setFilter(REPEATING);
//        trips= tripBean.getAllPrivateTrips();
//        assertEquals("getAllPrivateTrips should return 1 trip of the type Repeating", 1, trips.size());
//        assertEquals("getAllPrivateTrips should return 1 trip of the type Repeating", REPEATING, trips.get(0).getTitle());
//    }


}