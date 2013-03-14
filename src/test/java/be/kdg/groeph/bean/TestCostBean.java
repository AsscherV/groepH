package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.Cost;
import be.kdg.groeph.model.TripType;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.CostService;
import be.kdg.groeph.service.TripService;
import be.kdg.groeph.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.faces.bean.ManagedProperty;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestCostBean extends AbstractTransactionalJUnit4SpringContextTests {
    //TODO: Cannot add or update a child row: a foreign key constraint fails (`test`.`t_cost`, CONSTRAINT `FKCB5B90B89F0B976F` FOREIGN KEY (`tripUser`) REFERENCES `t_user` (`id`))
    @Qualifier("costBean")
    @Autowired
    CostBean costBean;

    @Qualifier("loginBean")
    @Autowired
    LoginBean loginBean;

    @ManagedProperty(value = "#{userService}")
    @Autowired
    UserService userService;

    @ManagedProperty(value = "#{costService}")
    @Autowired
    CostService costService;

    @Qualifier("tripBean")
    @Autowired
    TripBean tripBean;
    @ManagedProperty(value = "#{tripService}")
    @Autowired
    TripService tripService;

    TripUser user;

    @Before
    public void init() {
        user = UserMother.validUser1();
        loginBean.setUser(UserMother.validUser1());
        tripService.addTripType(new TripType("Timebound"));
        tripService.addTripType(new TripType("Public"));
        tripService.addTripType(new TripType("Private"));
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setTitle("trip");
        tripBean.setDescription("Een publieke test trip");
        tripBean.setStartTime(cal.getTime());
        cal.set(2013, Calendar.MARCH, 29, 12, 00);
        tripBean.setEndTime(cal.getTime());
        tripBean.setLabel("Test");
        tripBean.setTripType("Timebound");
        tripBean.setPublic(true);
        tripBean.addTrip();
    }

    @Test
    public void testAddCost() throws SQLException, IOException {
        costBean.setCostValue("12");
        costBean.setText("FOOD");
        costBean.addCost();

        assertEquals("Texts should be equal", "FOOD", costService.getCostByCostId(2).getText());
    }

    @Test
    public void testUpdateCost() throws SQLException, IOException {
        costBean.setCostValue("12");
        costBean.setText("FOOD");
        costBean.addCost();
        costBean.setNewcostValue("30");
        costBean.setNewtext("BEER");
        costBean.updateCost();

        assertEquals("Texts should be equal", "BEER", costService.getCostByCostId(3).getText());
    }

    @Test
    public void testDeleteCost() throws SQLException, IOException {
        costBean.setCostValue("12");
        costBean.setText("FOOD");
        costBean.addCost();
        costBean.deleteCost(costBean.getCurrentCost());

        assertEquals("size should be equal", 0, costService.getCostsByTrip(costBean.getCurrentTrip()).size());
    }

    @Test
    public void testEditCost() throws SQLException, IOException {

        costBean.setCostValue("12");
        costBean.setText("FOOD");
        costBean.addCost();
        costBean.editCost(costBean.getCurrentCost());

        assertEquals("Texts should be equal", "FOOD", costBean.getNewtext());
    }

    @Test
    public void testCancelCost() throws SQLException, IOException {

        costBean.setCostValue("12");
        costBean.setText("FOOD");
        costBean.addCost();
        costBean.editCost(costBean.getCurrentCost());
        costBean.cancel();

        assertEquals("Texts should be equal", "", costBean.getNewtext());
    }

    @Test
    public void testGetHasCostsWithNoCost() throws SQLException, IOException {

        assertEquals(" should be false", false, costBean.getHasCosts());
    }

    @Test
    public void testGetHasCostsWithCost() throws SQLException, IOException {
        costBean.setCostValue("12");
        costBean.setText("FOOD");
        costBean.addCost();
        assertEquals(" should be true", true, costBean.getHasCosts());
    }

    @Test
    public void testGetConfirmedUsers() throws SQLException, IOException {

        assertEquals(" should be 1", 1, costBean.getConfirmedTripUsers().size());
    }

    @Test
    public void testGetTotalCostPerUser() throws SQLException, IOException {

        assertEquals(" should be 0.0", (Object) 0.0, costBean.getTotalCostPerUser(loginBean.getUser()));
    }

    @Test
    public void testGetCostsPerTripUser() throws SQLException, IOException {

        assertEquals(" should be 0.0", (Object) 0.0, costBean.getCostsPerTripUser().get(0).getCostValue());
    }

    @Test
    public void testIsOrganiser() throws SQLException, IOException {

        assertEquals(" should be true", true, costBean.isOrganizer());
    }
}

