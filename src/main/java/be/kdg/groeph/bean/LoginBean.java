package be.kdg.groeph.bean;

import be.kdg.groeph.model.User;
import be.kdg.groeph.service.LoginService;
import be.kdg.groeph.service.UserService;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 * To change this template use File | Settings | File Templates.
 */
@Component
@Named
@SessionScoped
public class LoginBean implements Serializable {
    static Logger logger = Logger.getLogger(LoginBean.class);

    @ManagedProperty(value = "#{loginService}")
    @Autowired
    LoginService loginService;

    @NotEmpty(message = "{email} {notempty}")
    private String email;
    @NotEmpty(message = "{password} {notempty}")
    private String password;

    private boolean isLoggedIn;

    User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String loginUser() {
        User userLogin = loginService.loginUser(getEmail(), getPassword());
        if (userLogin.isNull()) {
            return "FAILURE";
        }   else {
            user = userLogin;
            isLoggedIn = true;
            return "SUCCESS";
        }
    }
}
