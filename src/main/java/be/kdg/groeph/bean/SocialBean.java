package be.kdg.groeph.bean;
import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.LoginService;
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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
    private String name ;
    private String caption ;
    private  String description ;
    public SocialBean() {
        loggedIn = false;
    }
    public void getFacebookUrlAuth() throws UnsupportedEncodingException {

       redirectUrl = "http://localhost:8080/groepH-1.0/pages/index.xhtml";

       redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");

       url = "https://www.facebook.com/dialog/oauth?client_id="
                + appId + "&redirect_uri=" + redirectUrl
                + "&scope=email,user_birthday,user_hometown&response_type=token";
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {
        }
    }
    public String login() {
        facebookClient = new DefaultFacebookClient(access_token);
        fbUser = facebookClient.fetchObject("me", User.class);

        user = userService.getUserByEmail(fbUser.getEmail());
        if (user.isNull()) {
            String newPassword = makeNewUserWithPassword();
            mailService.uponFacebookLoginNoAccount(user.getEmail(), newPassword);
            return setAndLoginUser(newPassword, false);
        } else {
            return setAndLoginUser( user.getPassword(),true);
        }
    }
    private String setAndLoginUser(String password, boolean isEncrypted) {
        TripUser loggedInUser;
        try {
            if(isEncrypted){
                loggedInUser = loginService.loginUser(user.getEmail(), password);
            }else{
                loggedInUser = loginService.loginUser(user.getEmail(), SHAEncryption.encrypt(password));
            }
            if (loggedInUser.isNull()) {
                return "FAILURE";
            } else {
                loginBean.setUser(loggedInUser);
                loginBean.setLoggedIn(true);
                loggedIn = true;
                return "SUCCESS";
            }
        } catch (Exception e) {
            return "FAILURE";
        }
    }
    private String makeNewUserWithPassword() {
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
        return newPassword;
    }
    public void logout() {
        loggedIn = false;
        facebookClient = null;
        user = null;
    }

    public void postTripOnFacebook() throws UnsupportedEncodingException {
            setNameCaptionDescription();

            redirectUrl = "http://localhost:8080/groepH-1.0/pages/user/trip.xhtml";

               redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");

                 url = " https://www.facebook.com/dialog/feed?app_id="+ appId
                         + "&link=" + redirectUrl
                         + "&name="+ name
                         + "&caption="+ caption
                        + "&description="+ description
                         +"&redirect_uri=" + redirectUrl;
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                } catch (Exception e) {
                }
    }

    public void sendMessageToFriendsOnFacebook() throws UnsupportedEncodingException {
        setNameCaptionDescription();
        redirectUrl = "http://localhost:8080/groepH-1.0/pages/user/participants.xhtml";

                       redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
                         url = " https://www.facebook.com/dialog/send?app_id="+ appId
                                 + "&name="+ name
                                 + "&link=" + "https://facebook.com"
                                 +"&redirect_uri=" + redirectUrl;
                        try {
                            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
                        } catch (Exception e) {
                        }
        }

    private void setNameCaptionDescription() {
         name = "I'm going on a trip named "+ tripBean.getCurrentTrip().getTitle() +" with Trippy Travaler !" ;
         caption = "TrippyLink" ;
         description = tripBean.getCurrentTrip().getDescription();
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