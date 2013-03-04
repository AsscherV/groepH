package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestRegisterBean extends AbstractTransactionalJUnit4SpringContextTests {
    @Qualifier("registerBean")
    @Autowired
    RegisterBean registerBean;

    @Test
    public void insertValidUser() throws ParseException {
        fillRegisterBean();
        assertEquals("SUCCESS",registerBean.addUser());
    }

    @Test
    public void insertInValidUser() throws ParseException {
        fillRegisterBean();
        registerBean.setPassword("testpassword");
        registerBean.setSecondPassword("testpasswordooo");
        assertEquals("FAILURE",registerBean.addUser());
    }

    @Test
    public void testValidPassword(){
        registerBean.setPassword("wachtwoord");
        registerBean.setSecondPassword("wachtwoord");
        assertTrue("Both passwords must be Equal",registerBean.confirmPassword());
    }

    @Test
    public void testInvalidPassword(){
        registerBean.setPassword("wachtwoord");
        registerBean.setSecondPassword("wachtwoordo");
        assertFalse("Passwords are not Equal",registerBean.confirmPassword());
    }

    public void fillRegisterBean(){
        registerBean.setGender('M');
        registerBean.setFirstName("Gunther");
        registerBean.setLastName("Laurijssens");
        registerBean.setEmail("guntherlaurijssens@gmail.com");
        registerBean.setPassword("testpassword");
        registerBean.setSecondPassword("testpassword");
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        registerBean.setDateOfBirth(cal.getTime());
        registerBean.setStreet("grote Baan");
        registerBean.setStreetNumber("7");
        registerBean.setZipcode("2380");
        registerBean.setCity("Ravels");
        registerBean.setPhoneNumber("04989898989");
    }

    public void testUpdateUser(){

    }
}
