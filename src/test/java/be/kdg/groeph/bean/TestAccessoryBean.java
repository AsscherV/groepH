package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.UserMother;
//import be.kdg.groeph.model.Label;
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
    public void init() {
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
        //ArrayList<Label> lbls = new ArrayList<Label>();
        tripBean.setLabel("Test");
        /*lbls.add(new Label("Test"));
        lbls.add(new Label("Test1"));
        lbls.add(new Label("Test2"));
        lbls.add(new Label("Test3"));
        lbls.add(new Label("Test4"));
        tripBean.setLabels(lbls);   */
        tripBean.setTripType("Timebound");
        tripBean.setPublic(true);
        tripBean.addTrip();
    }

    @Test
    public void testIsAddedConfirmedUser() {
        accessoryBean.setDescription(COOKIES);
        accessoryBean.getCurrentAccessory().setChecked(false);
        accessoryBean.addAccessory();
        assertEquals("The user is an added confirmed user of the accessory", false, accessoryBean.isAddedConfirmedUser(accessoryBean.getCurrentAccessory()));
    }

    @Test
    public void testUpdateChecked() {
        accessoryBean.setDescription(COOKIES);
        accessoryBean.getCurrentAccessory().setChecked(false);

        accessoryBean.addAccessory();
        Trip trip = tripService.getTripById(9);
        assertEquals("The accessory checked field should be false", false, trip.getAccessories().get(0).isChecked());
        accessoryBean.updateChecked(accessoryBean.getCurrentAccessory());
        assertEquals("The accessory checked field should be true", true, trip.getAccessories().get(0).isChecked());
    }

    @Test
    public void testHasAddableConfirmedTripUsers() {
        accessoryBean.setDescription(COOKIES);
        accessoryBean.addAccessory();
        Trip trip = tripService.getTripById(4);
        assertEquals("The accessory should have 0 addable confirmed trip users and the method should return false ", false, accessoryBean.hasAddableTripUsers(trip.getAccessories().get(0)));
    }

    @Test
    public void testAddableConfirmedTripUsers() {
        accessoryBean.setDescription(COOKIES);
        accessoryBean.addAccessory();
        Trip trip = tripService.getTripById(6);
        assertEquals("The accessory should have 0 addable confirmed trip users ", 0, accessoryBean.addableConfirmedTripUsers(trip.getAccessories().get(0)).size());
    }

    @Test
    public void testAddAccessoryNoUser() {
        accessoryBean.setDescription(A_GOOD_SENSE_OF_HUMOR);
        String redirect = accessoryBean.addAccessory();
        Trip trip = tripService.getTripById(7);
        assertEquals("AddAccessory should return SUCCESS", Tools.SUCCESS, redirect);
        assertEquals("The accessory 'A good sense of humor' should be present in the database linked to trip '" + ACCESSORYTEST + "'", A_GOOD_SENSE_OF_HUMOR, trip.getAccessories().get(0).getDescription());
    }

    @Test
    public void testAddAccessoryWithUser() {
        accessoryBean.setDescription(COOKIES);
        accessoryBean.setUser(UserMother.validUser2());
        accessoryBean.setUser(UserMother.validUser3());
        String redirect = accessoryBean.addAccessory();

        accessoryBean.addUser();
        accessoryBean.addUser();
        Trip trip = tripService.getTripById(8);
        assertEquals("AddAccessory should return SUCCESS", Tools.SUCCESS, redirect);
        assertEquals("The accessory 'Cookies' should be present in the database linked to trip '" + ACCESSORYTEST + "' and linked to 2 users", 2, trip.getAccessories().get(0).getTripUsers().size());

    }

    @Test
    public void testUpdateAccessory() {
        accessoryBean.setDescription(A_GOOD_SENSE_OF_HUMOR);
        accessoryBean.addAccessory();
        accessoryBean.setNewdescription(COOKIES);
        accessoryBean.updateAccessory();
        Trip trip = tripService.getTripById(5);
        assertEquals("The accessory 'a good sense of humor' should become 'cookies'", COOKIES, trip.getAccessories().get(0).getDescription());
    }

    @Test
    public void testDeleteAccessory() {
        accessoryBean.setDescription(A_GOOD_SENSE_OF_HUMOR);
        accessoryBean.addAccessory();
        Trip trip = tripService.getTripById(3);
        assertEquals("the accessory list should be 1", 1, trip.getAccessories().size());
        accessoryBean.deleteAccessory(accessoryBean.getCurrentAccessory());
        assertEquals("the accessory list should be 0", 0, trip.getAccessories().size());
    }

    @Test
    public void testRemoveUserFromAccessory() {
        accessoryBean.setDescription(COOKIES);
        accessoryBean.setUser(UserMother.validUser2());
        String redirect = accessoryBean.addAccessory();

        accessoryBean.addUser();
        Trip trip = tripService.getTripById(1);
        assertEquals("The accessory should have 1 user ", 1, trip.getAccessories().get(0).getTripUsers().size());
        accessoryBean.removeUser(accessoryBean.getCurrentAccessory());
        assertEquals("The accessory should have 0 users ", 0, trip.getAccessories().get(0).getTripUsers().size());
    }


}
