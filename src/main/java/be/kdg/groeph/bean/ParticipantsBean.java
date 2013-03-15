package be.kdg.groeph.bean;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.service.MailService;
import be.kdg.groeph.service.ParticipantsService;
import be.kdg.groeph.service.TripService;
import org.apache.log4j.Logger;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.util.ArrayList;

@Component
@Named
@ViewAccessScoped
public class ParticipantsBean implements Serializable {
    static Logger logger = Logger.getLogger(ParticipantsBean.class);

    String emails;
    ArrayList<String> invalidEmails = new ArrayList<String>();
    ArrayList<String> validEmails = new ArrayList<String>();
    boolean hasInvalidEmails;

    @ManagedProperty(value = "#{mailService}")
    @Autowired
    MailService mailService;

    @ManagedProperty(value = "#{participantsService}")
    @Autowired
    ParticipantsService participantsService;

    @Autowired
    @Resource(name = "tripBean")
    private TripBean tripBean;

    public ParticipantsBean() {
        hasInvalidEmails = false;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public boolean isHasInvalidEmails() {
        return hasInvalidEmails;
    }

    public void setHasInvalidEmails(boolean hasInvalidEmails) {
        this.hasInvalidEmails = hasInvalidEmails;
    }

    public ArrayList<String> getInvalidEmails() {
        return invalidEmails;
    }

    public void setInvalidEmails(ArrayList<String> invalidEmails) {
        this.invalidEmails = invalidEmails;
    }

    public boolean sendInvitations() {
        try {
            if (validMails(emails)) {
                Trip trip = tripBean.getCurrentTrip();
                participantsService.addUsersToTrip(validEmails, trip);
                logger.info("Invitation for trip: " + trip.getTitle() + " has been send to: " + validEmails);
                return true;
            } else {
                logger.info("Invitation for trip: " + tripBean.currentTrip.getTitle() + " has not been send");
                return false;
            }
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return false;
        }
    }

    public boolean isValidMail(String mail) {
        try {
            InternetAddress internetAddress = new InternetAddress(mail);
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public boolean validMails(String pEmails) {
        try {
            String[] emails = pEmails.split(";");
            invalidEmails.clear();
            validEmails.clear();

            for (int i = 0; i < emails.length; i++) {
                if (isValidMail(emails[i].trim())) {
                    validEmails.add(emails[i].trim());
                    if (!(invalidEmails.size() > 0)) {
                        hasInvalidEmails = false;
                    }
                } else {
                    hasInvalidEmails = true;
                    invalidEmails.add(emails[i].trim());
                }
            }

            if (invalidEmails.size() > 0) {
                return false;
            } else {
                return true;
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        }
    }
}
