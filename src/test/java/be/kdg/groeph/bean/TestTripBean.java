package be.kdg.groeph.bean;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.util.TripType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: Frederik
 * Date: 7/02/13
 * Time: 15:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestTripBean extends AbstractTransactionalJUnit4SpringContextTests {

    private final String description = "Lekker int antwaarps zonneke gon ligge op't mooiste strand vant land";
    private final String strand = "Strand";
    private final String antwerpen = "Antwerpen";
    private final String uitstap = "Uitstap";
    private final TripType timebound = TripType.TIMEBOUND;
    private final String title = "Chille op Sint-Anna plage";
    private Calendar cal;
    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;


    @Before
    public void init()
    {
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
    }

    @Test
    public void insertValidPublicTrip()
    {
        setTripBean(true,true);
        assertEquals("addTrip result should be SUCCESS","SUCCESS",tripBean.addTrip());
    }

    private void setTripBean(boolean isValid,boolean isPublic) {
        if(isValid)
        {
            tripBean.setTitle(title);
        }
        tripBean.setDescription(description);
        tripBean.addLabel(strand);
        tripBean.addLabel(antwerpen);
        tripBean.addLabel(uitstap);
        tripBean.setType(timebound);
        tripBean.setStartTime(cal.getTime());
        tripBean.setPublic(true);
    }

    @Test
    public void insertInvalidPublicTripNoTitle()
    {
        setTripBean(false,true);
        assertEquals("addTrip result should be FAILURE","FAILURE",tripBean.addTrip());
    }



}
