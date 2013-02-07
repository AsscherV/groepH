package be.kdg.groeph.bean;

import be.kdg.groeph.mockMother.UserMother;
import be.kdg.groeph.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

/**
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class UserTest extends AbstractTransactionalJUnit4SpringContextTests{


    @Qualifier("userBean")
    @Autowired
    UserBean userBean;

    private User user1;
    private User user2;

    @Before
    public void init(){
        user1 = UserMother.validUser1();
        user2 = UserMother.validUser2();
    }

    @Test
    public void insertUser(){
        /*
        Calendar cal;
        cal = Calendar.getInstance();
        cal.set(1988, Calendar.DECEMBER, 10);
        Address address=new Address("Baanhof","76","2330","Merksplas");
        user1 = new User("Gunther", "Laurijssens", cal.getTime(),"0498216718", 'M',"guntherlaurijssens@gmail.com","wachtwoord",address, new Date(), "User");
        user2 = new User("Gunther2", "Laurijssens", cal.getTime(),"0498216718", 'M',"guntherlaurijssens@gmail.com","wachtwoord",address, new Date(), "User");
        assertNotSame(user1.getFirstName(), user2.getLastName());
        */
        assertNotSame(user1, user2);
    }


    @Test
    public void testValidPassword(){
        userBean.setPassword("wachtwoord");
        userBean.setSecondPassword("wachtwoord");
        assertTrue(userBean.confirmPassword());
    }

    @Test
    public  void testInvalidPassword(){
        userBean.setPassword("wachtwoord");
        userBean.setSecondPassword("wachtwoordo");
        assertFalse(userBean.confirmPassword());
    }



}
