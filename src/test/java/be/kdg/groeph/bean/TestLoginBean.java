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
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.security.auth.login.LoginException;

import static org.junit.Assert.*;

/**
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestLoginBean extends AbstractTransactionalJUnit4SpringContextTests {

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;
    @Autowired
    UserService userService;
    @Autowired
    UserDao userDao;

    TripUser validUser;

    @Before
    public void init(){
        validUser =  UserMother.validUser2();
        userDao.addUser(validUser);
    }

    @Test
    public void testLogin() throws LoginException {
        loginBean.setEmail("greg.deckers@student.kdg.be");
        loginBean.setPassword("password");
        assertEquals("SUCCESS", loginBean.loginUser());
    }

    @Test
    public void testInvalidLogin() throws LoginException {
        loginBean.setEmail("greg.deckers@student.kdg.be");
        loginBean.setPassword("qsdqs");
        assertEquals("FAILURE", loginBean.loginUser());
    }

    //TODO logout nog testen
    @Test
    public void testLogOut() throws LoginException {
        loginBean.setEmail("greg.deckers@student.kdg.be");
        loginBean.setPassword("password");
        loginBean.loginUser();
        assertEquals("SUCCESS",loginBean.logOut());

    }

}
