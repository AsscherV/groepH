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

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestRegisterBean extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";
    private static final String VALID_EMAIL1 = "guntherlaurijssens@gmail.com";

    @Qualifier("registerBean")
    @Autowired
    RegisterBean registerBean;

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Qualifier("userDaoImpl")
    @Autowired
    UserDao userDao;

    public void fillRegisterBeanWithValidData() {
        registerBean.setGender('M');
        registerBean.setFirstName("Gunther");
        registerBean.setLastName("Laurijssens");
        registerBean.setEmail(VALID_EMAIL1);
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

    @Test
    public void testAddUser() throws ParseException {
        fillRegisterBeanWithValidData();
        assertEquals(SUCCESS, registerBean.addUser());
        fillRegisterBeanWithValidData();
        assertEquals(FAILURE, registerBean.addUser());
    }

    @Test
    public void testConfirmPassword() {
        registerBean.setPassword("wachtwoord");
        registerBean.setSecondPassword("wachtwoord");
        assertTrue("Both passwords must be Equal", registerBean.confirmPassword());

        registerBean.setPassword("wachtwoord");
        registerBean.setSecondPassword("wachtwoordo");
        assertFalse("Passwords are not Equal", registerBean.confirmPassword());
    }

    @Test
    public void testInvalidUserData() throws ParseException {
        fillRegisterBeanWithValidData();
        registerBean.setPassword("testpassword");
        registerBean.setSecondPassword("testpasswordooo");
        assertEquals("ConfirmPassword fails, passwords are not equal: Expected FAILURE",FAILURE, registerBean.addUser());
    }

    @Test
    public void testUpdateUser() throws SQLException, ParseException {
        registerUser();
        assertFalse("User is not editable, return FALSE", registerBean.isEditableUser());
        assertNull("Returns null after getting data from current user", registerBean.editUser());
        assertTrue("User is editable, returns TRUE",registerBean.isEditableUser());
        registerBean.putNewValues(VALID_EMAIL1, "none", "none",new Date(),"none", 'V',"none","none","none","none");
        assertNull("User Update succeeds, returns null", registerBean.updateUser());
        assertFalse("User is not editable, return FALSE",registerBean.isEditableUser());
        assertNotEquals("Firstname changed from Gunther to none","none",userDao.getUserByEmail(VALID_EMAIL1).getFirstName());
    }

    @Test
    public void testCancelUpdate() throws ParseException {
        registerUser();
        assertFalse("User is not editable, return FALSE",registerBean.isEditableUser());
        assertNull("Returns null after getting data from current user", registerBean.editUser());
        assertTrue("User is editable, returns TRUE",registerBean.isEditableUser());
        assertNull("Update Cancelled, return null", registerBean.cancel());
        assertFalse("User is not editable, return FALSE",registerBean.isEditableUser());
    }

    public void registerUser() throws ParseException {
        fillRegisterBeanWithValidData();
        registerBean.addUser();
        loginBean.setUser(userDao.getUserByEmail(VALID_EMAIL1));
    }
}
