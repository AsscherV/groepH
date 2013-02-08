package be.kdg.groeph.dao;

import be.kdg.groeph.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.Assert.*;

/**
 * To change this template use File | Settings | File Templates.
 */

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestUserDao extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private UserDao userDao;

    private User user1;
    private User user2;

    @Test
    public void testInvalidEmail(){
        assertEquals(User.INVALID_USER(), userDao.getUserByEmail("geenemail"));
    }



}