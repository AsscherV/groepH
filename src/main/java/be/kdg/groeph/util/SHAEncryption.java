package be.kdg.groeph.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHAEncryption {
    static Logger logger = Logger.getLogger(SHAEncryption.class);

    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes("UTF-8"));
            byte byteData[] = md.digest();
            return new String(new Base64().encode(byteData), "UTF8");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Algorithm not found", e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Encoding not supported", e);
        }
    }
}
