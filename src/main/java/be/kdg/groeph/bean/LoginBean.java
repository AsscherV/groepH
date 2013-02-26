package be.kdg.groeph.bean;

import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.service.LoginService;
import be.kdg.groeph.service.UserService;
import be.kdg.groeph.util.SHAEncryption;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.security.auth.login.LoginException;
import java.io.Serializable;

@Component
@Named
@SessionScoped
public class LoginBean implements Serializable {
    public static final String RESET = "RESET";
    //static Logger logger = org.slf4j.LoggerFactory.getLogger(LoginBean.class);
    static Logger logger = Logger.getLogger(LoginBean.class);
    private static final String SUCCESS = "SUCCESS";
    private static final String FAILURE = "FAILURE";

    @ManagedProperty(value = "#{loginService}")
    @Autowired
    LoginService loginService;

    @Autowired
    UserService userService;

    @NotEmpty(message = "{email} {notempty}")
    @Email(message = "{email} {validEmail}")
    private String email;
    @NotEmpty(message = "{password} {notempty}")
    private String password;
    @NotEmpty(message = "{password} {notempty}")
    private String secondPassword;

    private boolean isLoggedIn;

    TripUser user;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String password2) {
        this.secondPassword = password2;
    }

    public TripUser getUser() {
        return user;
    }

    public void setUser(TripUser user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String loginUser() throws LoginException{
        try {
            user = loginService.loginUser(email, SHAEncryption.encrypt(password));
            if (user.isNull()) {
                return FAILURE;
            }
            else if(user.getTempPassword()!=null && user.getTempPassword().equals(SHAEncryption.encrypt(password)))
            {
                userService.changePassword(user,user.getTempPassword());
                isLoggedIn = true;
                return RESET;
            }
           else{

                isLoggedIn = true;
                return SUCCESS;
             }
            }
         catch (Exception e) {
            return FAILURE;
        }
    }


    public String logOut() {
        isLoggedIn = false;
        SecurityContextHolder.getContext().setAuthentication(null);
        //FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        return SUCCESS;
    }

    public String tempPasswordLogin() {
        if(password.equals(secondPassword)){
             userService.changePassword(user, SHAEncryption.encrypt(password));
        }
        return SUCCESS;

    }
}

