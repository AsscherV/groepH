package be.kdg.groeph.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.faces.bean.ManagedProperty;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:daoContext.xml"})
public class TestMailService extends AbstractTransactionalJUnit4SpringContextTests {
    private static final String VALID_EMAIL1 = "guntherlaurijssens@gmail.com";
    private static final String INVALID_EMAIL1 = "guntherlaurijssens";

    @ManagedProperty(value = "#{mailService}")
    @Autowired
    MailService mailService;

    @Test
    public void testIsSuccessfulRegistration(){
        assertTrue("Returns True if email was successfully sent",mailService.isSuccessfulRegistration(VALID_EMAIL1));
        assertFalse("Returns False if email was not successfully sent",mailService.isSuccessfulRegistration(INVALID_EMAIL1));
    }
}
