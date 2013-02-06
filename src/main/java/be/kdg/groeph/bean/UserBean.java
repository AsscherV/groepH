package be.kdg.groeph.bean;

import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.User;
import be.kdg.groeph.service.UserService;
import org.apache.log4j.Logger;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * To change this template use File | Settings | File Templates.
 */
@Component
@ViewAccessScoped
@Named
@ManagedBean(name="userBean")
public class UserBean implements Serializable {
    static Logger logger = Logger.getLogger(UserBean.class);

    @ManagedProperty(value="#{userService}")
    @Autowired
    UserService userService;

    @NotEmpty(message = "{firstName} {notempty}")
    @Length(max=50, message = "{firstName} {length}")
    @NotNull(message = "{firstName} {notempty}")
    private String firstName;
    @NotEmpty(message = "{lastName} {notempty}")
    @Length(max=50, message = "{lastName} {length}")
    private String lastName;
    @NotNull(message = "{dateOfBirth} {notempty}")
    @Past(message = "{dateOfBirth} {past}")
    private Date dateOfBirth;
    @Length(max=30, message = "{phoneNumber} {length}")
    private String phoneNumber;
    private char gender;
    @NotEmpty(message = "{email} {notempty}")
    @Email(message = "{email} {validEmail}")
    @Length(max=100, message = "{email} {length}")
    @Column(name="email", nullable = true, length = 100)
    private String email;
    @NotEmpty(message = "{password} {notempty}")
    private String password;
    @NotEmpty(message = "{password} {notempty}")
    private String secondPassword;
    @NotEmpty(message = "{firstName} {notempty}")
    private Date dateRegistered;
    @NotEmpty(message = "{firstName} {notempty}")
    private String zipcode;
    @NotEmpty(message = "{firstName} {notempty}")
    private String street;
    @NotEmpty(message = "{firstName} {notempty}")
    private String streetNumber;
    @NotEmpty(message = "{firstName} {notempty}")
    private String city;
    @NotEmpty(message = "{firstName} {notempty}")
    private String role;

    public UserBean() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecondPassword() {
        return secondPassword;
    }

    public void setSecondPassword(String secondPassword) {
        this.secondPassword = secondPassword;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String number) {
        this.streetNumber = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean confirmPassword() {
        return password.equals(secondPassword);
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void addUser() throws ParseException {
        Address address = new Address(street, streetNumber,zipcode,city);
        setRole("User");
        setDateRegistered(new Date());
        //todo dees nog aanpasse met die datum...
        //todo hier hebbek ook een encrypt method zetten voor password.
        User user = new User(getFirstName(), getLastName(), getDateOfBirth(), getPhoneNumber(), getGender(),getEmail(), getPassword(),address,getDateRegistered(),getRole());
        if(confirmPassword()){
            userService.addUser(user);
        }

        /*
        if(userService.addUser(user)){
            return "SUCCESS";
        }else{
            return "FALSE";
        }
        */

    }


}
