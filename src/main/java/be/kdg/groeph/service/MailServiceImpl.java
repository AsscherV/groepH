package be.kdg.groeph.service;

import be.kdg.groeph.bean.TripBean;
import be.kdg.groeph.dao.UserDao;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.util.RandomPassword;
import be.kdg.groeph.util.SHAEncryption;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

@Transactional
@Service("mailService")
public class MailServiceImpl implements MailService {
    static Logger logger = Logger.getLogger(MailServiceImpl.class);

    @Autowired
    @Resource(name = "mailSender")
    private MailSender mailSender;

   
    @Qualifier("userDaoImpl")
    @Autowired
    UserDao userDao;

    @Deprecated
    @Override
    public void uponSuccessfulRegistration(String email) {
       

        String text="The user '" + email + "' is successfully registered";
        String subject="User Registration successful";
        sendMail(email,subject,text);

    }

    private void sendMail(String email, String subject, String text) {
        SimpleMailMessage[] mailMessageArray = new SimpleMailMessage[1];

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailMessageArray[0] = message;

        System.out.println("Sending email ....");
        mailSender.send(mailMessageArray);
    }

    /*
    *This method is the improved version of uponSuccessfullRegistration(string)
     */
    @Override
    public boolean isSuccessfulRegistration(String email) {
        SimpleMailMessage[] mailMessageArray = new SimpleMailMessage[1];

        SimpleMailMessage message = new SimpleMailMessage();

        String toAddress = email;
        message.setTo(toAddress);
        message.setSubject("User Registration successful");
        message.setText("The user '" + toAddress + "' is successfully registered");
        mailMessageArray[0] = message;

        System.out.println("Sending email ....");
        try{
            mailSender.send(mailMessageArray);
            return true;
        }  catch (MailException e){
            return false;
        }

    }

    public boolean uponTripInvitation(ArrayList<String> emails, Trip trip) {
        SimpleMailMessage[] mailMessageArray = new SimpleMailMessage[1];

        SimpleMailMessage message = new SimpleMailMessage();

        message.setBcc(emails.toArray(new String[emails.size()]));

        message.setSubject("Trip invitation");
        message.setText("You are invited to: " + trip.getTitle());
        mailMessageArray[0] = message;

        System.out.println("Sending email ....");
        try{
            mailSender.send(mailMessageArray);
            return true;
        }  catch (MailException e){
            return false;
        }

    }

    @Override
    public boolean recoverPassword(String email) {
        TripUser userByEmail = userDao.getUserByEmail(email);
        if (userByEmail.isNull()) {
            return false;
        }
        String pw =  RandomPassword.generatePassword();
        userByEmail.setTempPassword(SHAEncryption.encrypt(pw) );

        try {
            userDao.updateUser(userByEmail);
            logger.info("User updated| TempPassword is set for user: " + userByEmail.getEmail());
        } catch (SQLException e) {
            logger.warn("User update of TempPassword for user: " + userByEmail.getEmail() + " failed");
            return false;
        }

        sendMail(email,"Password recovery","** This is an automated message -- please do not reply as you will not receive a response. **" + "\n \n This message is in response to your request to reset your account password. \n \n Username: " + userByEmail.getEmail() + "\n New password: " + pw);

        return true;
    }

    @Override
    public boolean uponFacebookLoginNoAccount(String email, String password) {
        SimpleMailMessage[] mailMessageArray = new SimpleMailMessage[1];

                SimpleMailMessage message = new SimpleMailMessage();

                String toAddress = email;
                message.setTo(toAddress);
                message.setSubject("Facebook user registration successful!");
                message.setText("You have registered with facebook if you want to login please use \n email: "+toAddress+" \n password: "+ password +" .");
                mailMessageArray[0] = message;

                System.out.println("Sending email ....");
                try{
                    mailSender.send(mailMessageArray);
                    return true;
                }  catch (MailException e){
                    return false;
                }

    }
}
