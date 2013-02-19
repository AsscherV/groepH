package be.kdg.groeph.service;

import be.kdg.groeph.dao.UserDao;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.annotation.Resource;

@Transactional
@Service("mailService")
public class MailServiceImpl implements MailService {
    static Logger logger = Logger.getLogger(MailServiceImpl.class);

    @Autowired
    @Resource(name = "mailSender")
    private MailSender mailSender;

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
}
