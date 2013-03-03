package be.kdg.groeph.bean;

import be.kdg.groeph.dao.CostDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.TripUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * <p/>
 * Date: 27/02/13
 * Time: 11:51
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestCostBean extends AbstractTransactionalJUnit4SpringContextTests {

    @Qualifier("costBean")
    @Autowired
    CostBean costBean;

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @Autowired
    UserDao userDao;


    @Autowired
    CostDao costDao;

    TripUser user;

    @Before
    public void init() {
        user = UserMother.validUser3WithOutEncryptedPassword();
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        userDao.addUser(user);
    }


    @Test
    public void testAddCost() throws SQLException, IOException {


        try {
            costBean.setCostValue("12");
            costBean.setText("FOOD");
            loginBean.setEmail(user.getEmail());
            loginBean.setPassword(user.getPassword());
            loginBean.loginUser();
            costBean.setTripUser();
            costBean.addCost();


        } catch ( LoginException e) {
            assertTrue(false);
        }

        assertEquals("texts should be equal",costDao.getCostByCostId(1).getText(),costBean.text);
    }
}

