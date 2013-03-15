package be.kdg.groeph.bean;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.LoginService;
import be.kdg.groeph.service.MailService;
import be.kdg.groeph.service.UserService;
import be.kdg.groeph.util.RandomPassword;
import be.kdg.groeph.util.SHAEncryption;
import be.kdg.groeph.util.Tools;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

@Component
@RequestScoped
@Named
public class SocialBean implements Serializable {
    static Logger logger = Logger.getLogger(SocialBean.class);
    @ManagedProperty(value = "#{userService}")
    @Autowired
    UserService userService;
    @ManagedProperty(value = "#{loginService}")
    @Autowired
    LoginService loginService;
    @Qualifier("loginBean")
    @ManagedProperty(value = "#{loginBean}")
    @Autowired
    LoginBean loginBean;
    @Qualifier("tripBean")
    @ManagedProperty(value = "#{tripBean}")
    @Autowired
    TripBean tripBean;
    @ManagedProperty(value = "#{mailService}")
    @Autowired
    MailService mailService;
    private String access_token = "";
    private FacebookClient facebookClient;
    private User fbUser;
    private boolean loggedIn;
    private TripUser user;
    private Address address = new Address();
    private String appId = "414428928649857";
    private String redirectUrl;
    private String url;
    private String name;
    private String caption;
    private String description;

    public SocialBean() {
        loggedIn = false;
    }

    public void getFacebookUrlAuth() {
        try {
            redirectUrl = "http://localhost:8080/groepH-1.0/pages/index.xhtml";

            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");

            url = "https://www.facebook.com/dialog/oauth?client_id="
                    + appId + "&redirect_uri=" + redirectUrl
                    + "&scope=email,user_birthday,user_hometown&response_type=token";

            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            logger.info("User logged in with facebook and gets redirected to 'index' ");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public String login() {
        try {
            facebookClient = new DefaultFacebookClient(access_token);
            fbUser = facebookClient.fetchObject("me", User.class);

            user = userService.getUserByEmail(fbUser.getEmail());
            if (user.isNull()) {
                String newPassword = makeNewUserReturnPassword();
                mailService.uponFacebookLoginNoAccount(user.getEmail(), newPassword);
                logger.info("User created with facebook.");
                return setAndLoginUser(newPassword, false);
            } else {
                return setAndLoginUser(user.getPassword(), true);
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private String setAndLoginUser(String password, boolean isEncrypted) {

        try {
            TripUser loggedInUser;
            if (isEncrypted) {
                loggedInUser = loginService.loginUser(user.getEmail(), password);
                logger.info("User: "+ loggedInUser.getEmail() +"  with previously existing account logs in with facebook.");
            } else {
                loggedInUser = loginService.loginUser(user.getEmail(), SHAEncryption.encrypt(password));
                logger.info("User: "+ loggedInUser.getEmail() +" without previously existing account logs in with facebook and his password gets encrypted.");
            }
            if (loggedInUser.isNull()) {
                logger.error("User could not be logged in.");
                return Tools.FAILURE;
            } else {
                loginBean.setUser(loggedInUser);
                loginBean.setLoggedIn(true);
                loggedIn = true;
                logger.info("User: " + loginBean.getUser().getEmail() + " is set and logged in");
                return Tools.SUCCESS;
            }
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return Tools.FAILURE;
        }
    }

    private String makeNewUserReturnPassword() {
        try {
            String newPassword = RandomPassword.generatePassword();
            char gender;
            if (fbUser.getGender().equals("male")) {
                gender = 'M';
            } else {
                gender = 'F';
            }
            if (fbUser.getHometownName() == null) {
                address = new Address("no streetname", "no streetnumber", "no zip", fbUser.getHometownName());
            }
            user = new TripUser(fbUser.getFirstName(), fbUser.getLastName(), fbUser.getBirthdayAsDate(), "no phonenumber", gender, fbUser.getEmail(), SHAEncryption.encrypt(newPassword), address, new Date(), "ROLE_USER");
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
            userService.addUser(user);
            logger.info("Generated password created for user: " + user.getEmail());
            return newPassword;
        } catch (NullPointerException e) {
            logger.error(e.toString());
            return null;
        }
    }

    public void logout() {
        try {
            loggedIn = false;
            facebookClient = null;
            user = null;
            logger.info("User logged out.");
        } catch (NullPointerException e) {
            logger.error(e.toString());
        }
    }

    public void postTripOnFacebook() {
        try {
            setNameCaptionDescription();

            redirectUrl = "http://localhost:8080/groepH-1.0/pages/user/trip.xhtml";

            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");

            url = " https://www.facebook.com/dialog/feed?app_id=" + appId
                    + "&link=" + redirectUrl
                    + "&name=" + name
                    + "&caption=" + caption
                    + "&description=" + description
                    + "&redirect_uri=" + redirectUrl;

            FacesContext.getCurrentInstance().getExternalContext().redirect(url);

            logger.info("Redirected to facebook to write on wall about trip: " + tripBean.getCurrentTrip().getTitle());
        } catch (NullPointerException|UnsupportedEncodingException e) {
                    logger.error(e.toString());
                } catch (IOException e) {
                    logger.error(e.toString());
                }
    }

    public void sendMessageToFriendsOnFacebook() {
        try {
            setNameCaptionDescription();
            redirectUrl = "http://localhost:8080/groepH-1.0/pages/user/participants.xhtml";

            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
            url = " https://www.facebook.com/dialog/send?app_id=" + appId
                    + "&name=" + name
                    + "&link=" + "https://facebook.com"
                    + "&redirect_uri=" + redirectUrl;

            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
            logger.info("Redirected to facebook to send message to friends on facebook about trip: " + tripBean.getCurrentTrip().getTitle());
        } catch (NullPointerException|UnsupportedEncodingException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    private void setNameCaptionDescription() {
        try {
            name = "I'm going on a trip named " + tripBean.getCurrentTrip().getTitle() + " with Trippy Travaler !";
            caption = "TrippyLink";
            description = tripBean.getCurrentTrip().getDescription();
            logger.info("name, caption and description for facebook has been set.");
        } catch (NullPointerException e) {
            logger.error(e.toString());
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}