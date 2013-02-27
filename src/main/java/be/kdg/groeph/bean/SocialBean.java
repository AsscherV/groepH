package be.kdg.groeph.bean;

import be.kdg.groeph.service.UserService;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;


@Component
@SessionScoped
@Named
public class SocialBean implements Serializable {
    static Logger logger = Logger.getLogger(SocialBean.class);

    @ManagedProperty(value = "#{userService}")
        @Autowired
        UserService userService;

    private String access_token ="";

    private FacebookClient facebookClient;
    private User user;
    private boolean loggedIn;
    private String name;
    private String birthday;
    private String email;

    public SocialBean() {
        loggedIn = false;
    }

    public void getFacebookUrlAuth() {
        String appId = "414428928649857";
        String redirectUrl = "http://localhost:8080/groepH-1.0/pages/index.xhtml";
        String url = "https://www.facebook.com/dialog/oauth?client_id="
                + appId + "&redirect_uri=" + redirectUrl
                + "&scope=email,user_birthday&response_type=token&display=popup";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {

        }
    }

    public void login() {
        if (!access_token.equals("")) {
            this.loggedIn = true;
            facebookClient = new DefaultFacebookClient(access_token);
            user = facebookClient.fetchObject("me", User.class);
            name = user.getFirstName();
            birthday = user.getBirthday();
            email = user.getEmail();

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
