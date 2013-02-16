package be.kdg.groeph.service;

import be.kdg.groeph.mockMother.TripMother;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestTripService extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    TripService tripService;


    private Trip validPublicTrip1;
    private Trip validPublicTrip2;

    @Before
    public void init(){
        validPublicTrip1 = TripMother.validPublicTrip1();
    }


    @Test
    public void testAddPublicTrip(){
        assertTrue(tripService.addTrip(validPublicTrip1));
    }



}
