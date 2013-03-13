package be.kdg.groeph.bean;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.mockMother.UserMother;
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
    private final String adminEmail = "gunther.laurijssens@student.kdg.be";
    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";
    private String recoverypassword = "testrecovery";

    @Before
    public void init() throws ParseException {
        TripUser tripUser1 = UserMother.validUser2();
        tripUser1.setEnabled(true);
        tripUser1.setAccountNonLocked(true);
        tripUser1.setAccountNonExpired(true);
        tripUser1.setCredentialsNonExpired(true);
        userDao.addUser(tripUser1);

        TripUser tripUser2 = UserMother.validAdmin();
        tripUser2.setEnabled(true);
        tripUser2.setAccountNonLocked(true);
        tripUser2.setAccountNonExpired(true);
        tripUser2.setCredentialsNonExpired(true);
        userDao.addUser(tripUser2);
    }

    @Test
    public void testLogin() throws LoginException {
        loginBean.setEmail(validEmail);
        loginBean.setPassword("password");
        setLoginBean(validEmail, "password");
        assertFalse("IsLoggedIn is FALSE before login", loginBean.isLoggedIn());
        assertEquals(SUCCESS, loginBean.loginUser());
        assertTrue("IsLoggedIn is TRUE after successful login", loginBean.isLoggedIn());
    }

    @Test
    public void testInvalidLogin() throws LoginException {
        loginBean.setEmail(validEmail);
        loginBean.setPassword("qsdqs");
        setLoginBean(validEmail, "qsdqs");
        assertFalse("IsLoggedIn is FALSE before login", loginBean.isLoggedIn());
        assertEquals(FAILURE, loginBean.loginUser());
        assertFalse("IsLoggedIn is FALSE after login", loginBean.isLoggedIn());
    }

    @Test
    public void testLogOut() throws LoginException {
        setLoginBean(validEmail, "password");
        loginBean.loginUser();
        assertEquals(SUCCESS, loginBean.logOut());
        assertFalse("IsLoggedIn is FALSE after logout", loginBean.isLoggedIn());
    }

    @Test
    public void testIsNotAdmin() throws LoginException {
        setLoginBean(validEmail, "password");
        loginBean.loginUser();
        assertFalse("User has no admin rights", loginBean.getUser().isAdmin());
    }

    @Test
    public void testIsAdmin() throws LoginException {
        setLoginBean(adminEmail, "password");
        loginBean.loginUser();
        assertTrue("User has  admin rights", loginBean.getUser().isAdmin());
    }

    public void setLoginBean(String email, String password) throws LoginException {
        loginBean.setEmail(email);
        loginBean.setPassword(password);
    }

    @Test
    public void testRecoverPasswordSucces() throws LoginException {
        recoverBean.setEmail(validEmail);
        recoverBean.recoverPassword();

        TripUser userByEmail = userDao.getUserByEmail(validEmail);
        TripUser user = loginService.loginUser(validEmail, userByEmail.getTempPassword());
        loginBean.setUser(user);

        loginBean.setPassword(recoverypassword);
        loginBean.setSecondPassword(recoverypassword);
        loginBean.tempPasswordLogin();
        assertEquals("The new password must be equal to the passwordfield in user", SHAEncryption.encrypt(recoverypassword), user.getPassword());
        assertTrue("TempPassword field must be empty", user.getTempPassword().isEmpty());
    }


}
