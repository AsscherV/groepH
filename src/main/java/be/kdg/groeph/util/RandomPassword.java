package be.kdg.groeph.util;

import org.apache.log4j.Logger;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Vincent
 * Date: 28/02/13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public final class RandomPassword {
    static Logger logger = Logger.getLogger(RandomPassword.class);

    public static String generatePassword() {
           String random = UUID.randomUUID().toString().substring(0, 8);
           return random;
       }
}
