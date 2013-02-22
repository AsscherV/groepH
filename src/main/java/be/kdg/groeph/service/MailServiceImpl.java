package be.kdg.groeph.service;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.TripUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

@Transactional
@Service("mailService")
public class MailServiceImpl implements MailService {
    static Logger logger = Logger.getLogger(MailServiceImpl.class);

    @Autowired
    @Resource(name = "mailSender")
    private MailSender mailSender;

    @Autowired
    UserDao userDao;

    @Override
    public void uponSuccessfulRegistration(String email) {
        SimpleMailMessage[] mailMessageArray = new SimpleMailMessage[1];

        SimpleMailMessage message = new SimpleMailMessage();

        String toAddress = email;
        message.setTo(toAddress);
        message.setSubject("User Registration successful");
        message.setText("The user '" + toAddress + "' is successfully registered");
        mailMessageArray[0] = message;

        System.out.println("Sending email ....");
        mailSender.send(mailMessageArray);
    }

    @Override
    public boolean recoverPassword(String email) {
        TripUser userByEmail = userDao.getUserByEmail(email);
        if (userByEmail.isNull()) {
            return false;
        }

        userDao.updateUser(randomString());

        //Random password in databank toevoegen
        //mail sturen met random password.
        return true;
    }

    public String randomString() {
        String random = UUID.randomUUID().toString();
        return random;
    }

}
