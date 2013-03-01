package be.kdg.groeph.bean;

import be.kdg.groeph.service.MailService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: Maarten.Aerts
 * Date: 19/02/13
 * Time: 14:25
 */
@Component
@Named
@RequestScoped
public class RecoverBean {
    static Logger logger = Logger.getLogger(RecoverBean.class);

    @ManagedProperty(value = "#{mailService}")
    @Autowired
    MailService mailService;

    @NotEmpty(message = "Email is not valid")
    @Email(message = "Email is not valid")
    @Length(max = 100, message = "Email is not valid")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean recoverPassword() {
        return mailService.recoverPassword(getEmail());
    }
}

