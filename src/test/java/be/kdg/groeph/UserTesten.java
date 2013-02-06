package be.kdg.groeph;

import be.kdg.groeph.dao.TestUserDao;
import be.kdg.groeph.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * To change this template use File | Settings | File Templates.
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class UserTesten {
   /*
    private final User user = new User();


    @Test
    public void createValidUser(){

    }
    */

}
