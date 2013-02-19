package be.kdg.groeph.service;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.TripUser;
import org.junit.Before;
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


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestUserService extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    UserService userService;

    private TripUser validUser1;
    private TripUser validUser2;

    @Before
    public void init(){
        validUser1 = UserMother.validUser1();
        validUser2 = UserMother.validUser2();
    }

    @Test
    public void testAddUser(){

        assertTrue("Adding validUser1 returns true",userService.addUser(validUser1));
        assertFalse("Can't add existing user, returns false",userService.addUser(validUser1));
        assertTrue("Adding validUser2 returns true",userService.addUser(validUser2));
    }


}
