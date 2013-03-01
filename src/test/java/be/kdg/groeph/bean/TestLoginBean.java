package be.kdg.groeph.bean;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.LoginService;
import be.kdg.groeph.util.SHAEncryption;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestLoginBean extends AbstractTransactionalJUnit4SpringContextTests {

    /*
    To force a commit
     */


    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Autowired
    UserDao userDao;

    @Qualifier("registerBean")
    @Autowired
    RegisterBean registerBean;

    @Autowired
    LoginService loginService;

    @Qualifier("recoverBean")
    @Autowired
    RecoverBean recoverBean;


    private final String validEmail = "greg.deckers@student.kdg.be";
    private String recoverypassword="testrecovery";

    @Before
    public void init() throws ParseException {
        fillRegisterBean();
        registerBean.addUser();
    }

    @Test
    public void testLogin() throws LoginException {
        loginBean.setEmail(validEmail);
        loginBean.setPassword("password");
        setLoginBean(validEmail, "password");
        //loginBean.setEmail("greg.deckers@student.kdg.be");
        //loginBean.setPassword("password");
        assertEquals("SUCCESS", loginBean.loginUser());
    }

    @Test
    public void testInvalidLogin() throws LoginException {
        loginBean.setEmail(validEmail);
        loginBean.setPassword("qsdqs");
        setLoginBean(validEmail, "qsdqs");
        //loginBean.setEmail("greg.deckers@student.kdg.be");
        //loginBean.setPassword("qsdqs");
        assertEquals("FAILURE", loginBean.loginUser());
    }

    @Test
    public void testLogOut() throws LoginException{
        setLoginBean(validEmail, "password");
        //loginBean.setEmail("greg.deckers@student.kdg.be");
        //loginBean.setPassword("password");
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
    @Test
    public void testRecoverPasswordSucces() throws LoginException {
        recoverBean.setEmail(validEmail);
        recoverBean.recoverPassword();

        TripUser userByEmail = userDao.getUserByEmail(validEmail);
        TripUser user= loginService.loginUser(validEmail,userByEmail.getTempPassword());
        loginBean.setUser(user);

        loginBean.setPassword(recoverypassword);
        loginBean.setSecondPassword(recoverypassword);
        loginBean.tempPasswordLogin();
        assertEquals("The new password must be equal to the passwordfield in user", SHAEncryption.encrypt(recoverypassword) , user.getPassword());
        assertTrue("TempPassword field must be empty",user.getTempPassword().isEmpty());

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
