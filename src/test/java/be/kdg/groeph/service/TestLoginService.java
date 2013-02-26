package be.kdg.groeph.service;

import be.kdg.groeph.bean.RecoverBean;
import be.kdg.groeph.bean.RegisterBean;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.TripUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;

import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Maarten.Aerts
 * Date: 21/02/13
 * Time: 15:18
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestLoginService extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    UserDao userDao;

    @Autowired
    LoginService loginService;

    @Qualifier("recoverBean")
    @Autowired
    RecoverBean recoverBean;

    @Qualifier("registerBean")
    @Autowired
    RegisterBean registerBean;

    private final String validEmail = "greg.deckers@student.kdg.be";

    @Before
    public void init() throws ParseException {
        fillRegisterBean();
        registerBean.addUser();

    }
    @Test
    public void testLoggedInTempPassword() throws LoginException {
        recoverBean.setEmail(validEmail);
        recoverBean.recoverPassword();

        TripUser userByEmail = userDao.getUserByEmail(validEmail);
        TripUser user= loginService.loginUser(validEmail,userByEmail.getTempPassword());
        assertNotNull("Login must return user",user)  ;

    }
    public void fillRegisterBean(){
        registerBean.setGender('M');
        registerBean.setFirstName("Greg");
        registerBean.setLastName("Deckers");
        registerBean.setEmail(validEmail);
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
