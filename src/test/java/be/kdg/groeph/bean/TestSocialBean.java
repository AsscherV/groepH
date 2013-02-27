package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.Label;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
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
public class TestSocialBean extends AbstractTransactionalJUnit4SpringContextTests {//extends AbstractTransactionalJUnit4SpringContextTests

    @ManagedProperty(value = "#{tripService}")
        @Autowired
        TripService tripService;

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;
    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    private Trip trip1;




    @Test
    public void testLoginFacebookUser(){
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setTitle("Lekker int antwaarps");
        tripBean.setDescription("Een publieke test trip");
        tripBean.setStartTime(cal.getTime());
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setEndTime(cal.getTime());
        tripBean.setLabel("Test");
        tripBean.newLabel();
        tripBean.setLabel("Test2");
        tripBean.newLabel();
        tripBean.setLabel("Test3");
        tripBean.newLabel();
        tripBean.setTripType("tijdspanne");
        tripBean.setPublic("Public");
        assertEquals("addTrip result should be SUCCESS for Open Trip","SUCCESS",tripBean.addTrip());
    }
    @Test
    public void testGetAllOpenTrips(){
        Calendar cal;
                cal = Calendar.getInstance();
                cal.set(2013, Calendar.MARCH, 29, 12, 00);
        Trip trip = new Trip("titel1", "desc", cal.getTime(), cal.getTime(),new ArrayList<Label>(),new TripType("type"), true);
        Trip trip2 = new Trip("titel2", "desc", cal.getTime(), cal.getTime(),new ArrayList<Label>(),new TripType("type"), true);
        Trip trip3 = new Trip("titel3", "desc", cal.getTime(), cal.getTime(),new ArrayList<Label>(),new TripType("type"), true);
        Trip trip4 = new Trip("titel4", "desc", cal.getTime(), cal.getTime(),new ArrayList<Label>(),new TripType("type"), false);
        tripService.addTrip(trip);
        tripService.addTrip(trip2);
        tripService.addTrip(trip3);
        tripService.addTrip(trip4);

        assertEquals("getOpenTrips should give a list of 3", 3, 0);
    }


}