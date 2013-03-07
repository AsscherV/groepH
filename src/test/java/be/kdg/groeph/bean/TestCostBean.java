//package be.kdg.groeph.bean;
//
//import be.kdg.groeph.mockMother.UserMother;
//import be.kdg.groeph.model.TripUser;
//import be.kdg.groeph.service.CostService;
//import be.kdg.groeph.service.UserService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import javax.faces.bean.ManagedProperty;
//import javax.security.auth.login.LoginException;
//import java.io.IOException;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:daoContext.xml"})
//public class TestCostBean extends AbstractTransactionalJUnit4SpringContextTests {
//      TODO: Cannot add or update a child row: a foreign key constraint fails (`test`.`t_cost`, CONSTRAINT `FKCB5B90B89F0B976F` FOREIGN KEY (`tripUser`) REFERENCES `t_user` (`id`))
//    @Qualifier("costBean")
//    @Autowired
//    CostBean costBean;
//
//    @Qualifier("loginBean")
//    @Autowired
//    LoginBean loginBean;
//
//    @ManagedProperty(value = "#{userService}")
//    @Autowired
//    UserService userService;
//
//    @ManagedProperty(value = "#{costService}")
//    @Autowired
//    CostService costService;
//
//    TripUser user;
//
//    @Before
//    public void init() {
//        user = UserMother.validUser3WithOutEncryptedPassword();
//        user.setEnabled(true);
//        user.setAccountNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setCredentialsNonExpired(true);
//        userService.addUser(user);
//    }
//
//    @Test
//    public void testAddCost() throws SQLException, IOException {
//        try {
//            costBean.setCostValue("12");
//            costBean.setText("FOOD");
//            loginBean.setEmail(user.getEmail());
//            loginBean.setPassword(user.getPassword());
//            loginBean.loginUser();
//            costBean.setTripUser();
//            costBean.addCost();
//        } catch ( LoginException e) {
//            assertTrue(false);
//        }
//
//        assertEquals("Texts should be equal",costService.getCostByCostId(1).getText(),costBean.text);
//    }
//}

