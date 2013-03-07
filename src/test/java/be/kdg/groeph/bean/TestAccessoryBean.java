package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.TripService;
import be.kdg.groeph.util.Tools;
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
public class TestAccessoryBean extends AbstractTransactionalJUnit4SpringContextTests {
    public static final String ACCESSORYTEST = "accessorytest";
    public static final String A_GOOD_SENSE_OF_HUMOR = "A good sense of humor";
    public static final String COOKIES = "Cookies";
    private TripUser user1;
    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Qualifier("accessoryBean")
    @Autowired
    AccessoryBean accessoryBean;
    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;
    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;


    @Before
    public void init()
    {
        user1 = UserMother.validUser1();
        loginBean.setUser(UserMother.validUser1());
        tripService.addTripType(new TripType("Timebound"));
        tripService.addTripType(new TripType("Public"));
        tripService.addTripType(new TripType("Private"));
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setTitle(ACCESSORYTEST);
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
    }

    @Test
    public void testAddAccessoryNoUser()
    {
        accessoryBean.setDescription(A_GOOD_SENSE_OF_HUMOR);
        String redirect=accessoryBean.AddAccessory();
        Trip trip = tripService.getTripById(1);
        assertEquals("AddAccessory should return SUCCESS", Tools.SUCCESS,redirect);
        assertEquals("The accessory 'A good sense of humor' should be present in the database linked to trip '"+ACCESSORYTEST + "'",A_GOOD_SENSE_OF_HUMOR,trip.getAccessories().get(0).getDescription());
    }
    @Test
    public void testAddAccessoryWithUser()
    {
        accessoryBean.setDescription(COOKIES);
        accessoryBean.addUser(UserMother.validUser2());
        accessoryBean.addUser(UserMother.validUser3());
        String redirect=accessoryBean.AddAccessory();
        Trip trip = tripService.getTripById(2);
        assertEquals("AddAccessory should return SUCCESS", Tools.SUCCESS,redirect);
        assertEquals("The accessory 'Cookies' should be present in the database linked to trip '"+ACCESSORYTEST +"' and linked to 2 users",2,trip.getAccessories().get(0).getTripUsers().size());

    }


}
