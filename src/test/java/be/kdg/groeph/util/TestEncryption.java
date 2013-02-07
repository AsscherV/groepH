package be.kdg.groeph.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * To change this template use File | Settings | File Templates.
 */

//@Transactional
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:serviceContext.xml"})
public class TestEncryption {//extends AbstractJUnit4SpringContextTests
    //@Test
    /*
    public void testEncryption() {
        String password = "randomwpassword";
        String encryptedpassword = SHAEncryption.encrypt(password);
        assertEquals("Encrypted passwords have to be the same",encryptedpassword, SHAEncryption.encrypt(password));
        assertNotSame(password, encryptedpassword);
    }
    */
}
