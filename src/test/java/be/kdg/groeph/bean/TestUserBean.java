package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.User;
import be.kdg.groeph.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.*;


/**
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestUserBean extends AbstractTransactionalJUnit4SpringContextTests{


    @Qualifier("userBean")
    @Autowired
    UserBean userBean;
    @Autowired
    UserService userService;

    private User user1;
    private User user2;

    @Before
    public void init(){
        user1 = UserMother.validUser1();
        user2 = UserMother.validUser2();
    }

    @Test
    public void insertValidUser() throws ParseException {
        //userBean.setRole("User");
        userBean.setGender('M');
        userBean.setFirstName("Gunther");
        userBean.setLastName("Laurijssens");
        userBean.setEmail("guntherlaurijssens@gmail.com");
        userBean.setPassword("testpassword");
        userBean.setSecondPassword("testpassword");
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        userBean.setDateOfBirth(cal.getTime());
        userBean.setStreet("grote Baan");
        userBean.setStreetNumber("7");
        userBean.setZipcode("2380");
        userBean.setCity("Ravels");
        userBean.setPhoneNumber("04989898989");
        assertEquals("SUCCESS",userBean.addUser());
    }

    @Test
    public void insertInValidUser() throws ParseException {
        userBean.setGender('M');
        userBean.setFirstName("Gunther");
        userBean.setLastName("Laurijssens");
        userBean.setEmail("guntherlaurijssens@gmail.com");
        userBean.setPassword("testpassword");
        userBean.setSecondPassword("testpasswordooo");
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        userBean.setDateOfBirth(cal.getTime());
        userBean.setStreet("grote Baan");
        userBean.setStreetNumber("7");
        userBean.setZipcode("2380");
        userBean.setCity("Ravels");
        userBean.setPhoneNumber("04989898989");
        assertEquals("FAILURE",userBean.addUser());
    }

    @Test
    public void testValidPassword(){
        userBean.setPassword("wachtwoord");
        userBean.setSecondPassword("wachtwoord");
        assertTrue(userBean.confirmPassword());
    }

    @Test
    public  void testInvalidPassword(){
        userBean.setPassword("wachtwoord");
        userBean.setSecondPassword("wachtwoordo");
        assertFalse(userBean.confirmPassword());
    }







}
