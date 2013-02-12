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

/**
 * Created with IntelliJ IDEA.
 * User: Frederik
 * Date: 7/02/13
 * Time: 15:03
 */


public class TestTripBean {

}
/*
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestTripBean {//extends AbstractTransactionalJUnit4SpringContextTests

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;

    private Trip trip1;


    @Before
    public void init()
    {
        trip1 = TripMother.validOpenTrip1();
    }


    //@Test
    //public void insertValidPublicTrip()
    //{
      //  setTripBean(true,true);
       // assertEquals("addTrip result should be SUCCESS","SUCCESS",tripBean.addTrip());
    //}


    @Test
    public void testAddValidOpenTrip(){

        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setTitle("Lekker int antwaarps zonneke gon ligge op't mooiste strand vant land");
        tripBean.setType("Publiek");   //
        tripBean.setDescription("Een publieke test trip");
        tripBean.setStartTime(new Date());
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setEndTime(new Date());
        tripBean.addLabel("Strand");
        tripBean.addLabel("Antwerpen");
        tripBean.addLabel("Slecht weer");

        assertEquals("addTrip result should be SUCCESS for Open Trip","SUCCESS",tripBean.addTrip());
    }


    //private void setTripBean(boolean isValid,boolean isPublic) {


        //if(isValid)
        //{
        //    tripBean.setTitle(title);
        //}  else {
        //    tripBean.setTitle("");
        //}
        //tripBean.setDescription(description);
        //tripBean.addLabel(strand);
        //tripBean.addLabel(antwerpen);
        //tripBean.addLabel(uitstap);
        //tripBean.setType(timebound);
        //tripBean.setStartTime(cal.getTime());
        //tripBean.setPublic(isPublic);
    //}


    //@Test
    //public void insertInvalidPublicTripNoTitle()
    //{

        //setTripBean(false,true);
        //assertEquals("addTrip result should be FAILURE","FAILURE",tripBean.addTrip());
    //}


}
*/
