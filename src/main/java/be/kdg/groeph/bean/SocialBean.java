package be.kdg.groeph.bean;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.MailService;
import be.kdg.groeph.service.UserService;
import be.kdg.groeph.util.RandomPassword;
import be.kdg.groeph.util.SHAEncryption;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;


@Component
@SessionScoped
@Named
public class SocialBean implements Serializable {
    static Logger logger = Logger.getLogger(SocialBean.class);

    @ManagedProperty(value = "#{userService}")
    @Autowired
    UserService userService;

    @Qualifier("loginBean")
    @ManagedProperty(value = "#{loginBean}")
    @Autowired
    LoginBean loginBean;

    @ManagedProperty(value = "#{mailService}")
        @Autowired
        MailService mailService;

    private String access_token = "";

    private FacebookClient facebookClient;
    private User fbUser;
    private boolean loggedIn;
    private String name;
    private String birthday;
    private String email;
    private TripUser user;

    public SocialBean() {
        loggedIn = false;
    }

    public void getFacebookUrlAuth() {
        String appId = "414428928649857";
        String redirectUrl = "http://localhost:8080/groepH-1.0/pages/index.xhtml";
        String url = "https://www.facebook.com/dialog/oauth?client_id="
                + appId + "&redirect_uri=" + redirectUrl
                + "&scope=email,user_birthday,user_hometown&response_type=token&display=popup";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {

        }
    }

    public String login() {
        TripUser user;
        Address address = new Address();

        loggedIn = true;
        facebookClient = new DefaultFacebookClient(access_token);
        fbUser = facebookClient.fetchObject("me", User.class);
        name = fbUser.getFirstName();
        birthday = fbUser.getGender();
        email = fbUser.getEmail();

        user = userService.getUserByEmail(fbUser.getEmail());

        if (user.isNull()) {
            String newPassword = RandomPassword.generatePassword();
            char gender;
            if (fbUser.getGender().equals("male")) {
                gender = 'M';
            } else {
                gender = 'F';
            }
            if (!fbUser.getHometownName().isEmpty()) {
                address = new Address("no streetname", "no streetnumber", "no zip", fbUser.getHometownName());
            }
            user = new TripUser(fbUser.getFirstName(), fbUser.getLastName(), fbUser.getBirthdayAsDate(), "no phonenumber", gender, fbUser.getEmail(), SHAEncryption.encrypt(newPassword), address, new Date(), "ROLE_USER");
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            userService.addUser(user);
            loginBean.setUser(user);
            loginBean.setLoggedIn(true);

            mailService.uponFacebookLoginNoAccount(user.getEmail(),newPassword);
            return "SUCCESS";
        } else {
            loginBean.setUser(user);
            loginBean.setLoggedIn(true);
            return "SUCCESS";
        }

    }

    public void logout() {
        loggedIn = false;
        facebookClient = null;
        user = null;
    }

    /*public String addUser() throws ParseException {
            Address address = new Address(getStreet(), getStreetNumber(), getZipcode(), getCity());
            setRole("ROLE_USER");
            setDateRegistered(new Date());
            TripUser user = new TripUser(getFirstName(), getLastName(), getDateOfBirth(), getPhoneNumber(), getGender(), getEmail(), SHAEncryption.encrypt(getPassword()), address, getDateRegistered(), getRole());
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            if (confirmPassword()) {
                boolean result = userService.addUser(user);
                if (result) {
                    userService.addUser(user);
                    mailService.uponSuccessfulRegistration(user.getEmail());
                    registered = true;
                    return SUCCESS;
                }
                return FAILURE;
            }
            return FAILURE;
        } */

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {

        this.loggedIn = loggedIn;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
