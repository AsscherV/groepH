package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.TripMother;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestTripBean extends AbstractTransactionalJUnit4SpringContextTests {//extends AbstractTransactionalJUnit4SpringContextTests

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;

    private Trip trip1;


    @Before
    public void init()
    {
    }

    @Test
    public void testAddValidOpenTrip(){

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



}