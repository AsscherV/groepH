package be.kdg.groeph.service;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.TripUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

/**
 * To change this template use File | Settings | File Templates.
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestUserService extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    UserService userService;

    private TripUser user1;
    private TripUser user2;

    @Test
    public void testAddUser(){
        user1 = UserMother.validUser1();
        user2 = UserMother.validUser2();
        assertTrue(userService.addUser(user1));
        assertFalse(userService.addUser(user1));
        assertTrue(userService.addUser(user2));
    }


}
