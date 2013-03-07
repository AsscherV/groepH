package be.kdg.groeph.bean;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.TripUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestRecoverBean  extends AbstractTransactionalJUnit4SpringContextTests {

    private final String invalidEmail = "invalid@test.com";
    private final String validEmail = "gunther.laurijssens@student.kdg.be";

    @Qualifier("recoverBean")
    @Autowired
    RecoverBean recoverBean;

    @Autowired
    private UserDao userDao;

    private TripUser user1;

    @Before
    public void init(){
        user1 = UserMother.validUser1();
        userDao.addUser(user1);
    }

    @Test
    public void testInvalidEmail(){
        recoverBean.setEmail(invalidEmail);
        assertFalse("Recover password must return false",recoverBean.recoverPassword());
    }


    @Test
    public void testValidEmail(){
        recoverBean.setEmail(validEmail);
        assertTrue("Recover password must return true",recoverBean.recoverPassword());
    }

    @Test
    public void testRecoverPasswordGeneration(){
        assertNull("TempPassword should be Null",userDao.getUserByEmail(validEmail).getTempPassword());
        recoverBean.setEmail(validEmail);
        recoverBean.recoverPassword();
        assertNotNull("TempPassword shouldn't be Null",userDao.getUserByEmail(validEmail).getTempPassword());
    }
}

