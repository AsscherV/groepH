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

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestTripBean extends AbstractTransactionalJUnit4SpringContextTests {
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
    private TripUser user1;

    @Before
    public void init() {
        user1 = UserMother.validUser1();
        loginBean.setUser(UserMother.validUser1());
        tripDao.addTripType(new TripType("Timebound"));
        tripDao.addTripType(new TripType("Public"));
        tripDao.addTripType(new TripType("Private"));
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
        tripBean.setTripType("Timebound");
        tripBean.setPublic(true);
        assertEquals("addTrip result should be SUCCESS for PUBLIC Trip", "SUCCESS", tripBean.addTrip());
    }

    @Test
    public void testGetAllPublicTrips() {
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        TripType type = tripDao.getTypeByName("Timebound");
        Trip trip = new Trip("titel1", "desc", cal.getTime(), cal.getTime(), new ArrayList<Label>(), type, true);
        Trip trip2 = new Trip("titel2", "desc", cal.getTime(), cal.getTime(), new ArrayList<Label>(), type, true);
        Trip trip3 = new Trip("titel3", "desc", cal.getTime(), cal.getTime(), new ArrayList<Label>(), type, true);
        Trip trip4 = new Trip("titel4", "desc", cal.getTime(), cal.getTime(), new ArrayList<Label>(), type, false);
        user1.addTrip(trip);
        user1.addTrip(trip2);
        user1.addTrip(trip3);
        user1.addTrip(trip4);
        tripService.addTrip(trip);
        tripService.addTrip(trip2);
        tripService.addTrip(trip3);
        tripService.addTrip(trip4);
        assertEquals("getOpenTrips should give a list of 3 public trips", 3, tripBean.getAllPublicTrips().size());
    }
}