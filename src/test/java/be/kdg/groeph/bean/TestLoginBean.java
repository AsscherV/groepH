package be.kdg.groeph.bean;

import be.kdg.groeph.dao.UserDao;
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

import javax.security.auth.login.LoginException;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestLoginBean extends AbstractTransactionalJUnit4SpringContextTests {


    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Qualifier("registerBean")
    @Autowired
    RegisterBean registerBean;

    @Before
    public void init() throws ParseException {
        fillRegisterBean();
        registerBean.addUser();
    }

    @Test
    public void testLogin() throws LoginException {
        setLoginBean("greg.deckers@student.kdg.be", "password");
        assertEquals("SUCCESS", loginBean.loginUser());
    }

    @Test
    public void testInvalidLogin() throws LoginException {
        setLoginBean("greg.deckers@student.kdg.be", "qsdqs");
        assertEquals("FAILURE", loginBean.loginUser());
    }

    @Test
    public void testLogOut() throws LoginException {
        setLoginBean("greg.deckers@student.kdg.be", "password");
        loginBean.loginUser();
        assertEquals("SUCCESS",loginBean.logOut());
    }

    @Test
    public void testIsAdmin(){
        assertFalse("User has no admin rights",loginBean.user.isAdmin());
    }

    public void setLoginBean(String email,String password){
        loginBean.setEmail(email);
        loginBean.setPassword(password);

    }

    public void fillRegisterBean(){
        registerBean.setGender('M');
        registerBean.setFirstName("Greg");
        registerBean.setLastName("Deckers");
        registerBean.setEmail("greg.deckers@student.kdg.be");
        registerBean.setPassword("password");
        registerBean.setSecondPassword("password");
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        registerBean.setDateOfBirth(cal.getTime());
        registerBean.setStreet("test");
        registerBean.setStreetNumber("test");
        registerBean.setZipcode("test");
        registerBean.setCity("test");
        registerBean.setPhoneNumber("04989898989");
    }



}
