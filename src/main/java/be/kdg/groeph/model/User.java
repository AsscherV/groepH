package be.kdg.groeph.model;

import be.kdg.groeph.model.Null.NullUser;
import be.kdg.groeph.model.Null.Nullable;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * To change this template use File | Settings | File Templates.
 */



@Entity
@Table(name="t_user")
public class User implements Nullable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="firstName", nullable = false, length = 100)
    private String firstName;
    @Column(name="lastName", nullable = false, length = 50)
    private String lastName;
    @Column(name="dateOfBirth", nullable = false, length = 100)
    private Date dateOfBirth;
    @Column(name="phoneNumber", nullable = false, length = 100)
    private String phoneNumber;
    @Column(name="gender", nullable = false, length = 100)        //true
    private char gender;
    @Column(name="email", nullable = false, length = 100)
    private String email;
    @Column(name="password", nullable = false, length = 100)
    private String password;
    @Column(name="role", nullable = false, length = 100)
    private String role;
    @Column(name="dateRegistered", nullable = false, length = 100)
    private Date dateRegistered;
    @Column (name="isAdmin", nullable = false)
    private Boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "address")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Address address;

    /*
        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Trip> trips = new ArrayList<Car>();
     */


    public User() {
    }

    public User(String firstName, String lastName, Date dateOfBirth, String phoneNumber, char gender, String email, String password, Address address, Date dateRegistered, String role, Boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.address = address;
        this.dateRegistered = dateRegistered;
        this.role = role;
        this.isAdmin = isAdmin;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public int hashCode() {
        int result = 31 * (getFirstName() != null ? getFirstName().hashCode() : 0);
        result += 31 * (getLastName() != null ? getLastName().hashCode() : 0);
        result += 31 * (getDateOfBirth() != null ? getDateOfBirth().hashCode() : 0);
        result += 17 * (getPhoneNumber() != null ? phoneNumber.hashCode() : 0);
        result += 31 * (getEmail() != null ? getEmail().hashCode() : 0);

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        User user = (User) obj;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dob = "";
        String userdob = "";

        try {
            dob = sdf.format(dateOfBirth);
            userdob = sdf.format(user.dateOfBirth);
        } catch (NullPointerException e) {
            //TODO hier nog deftige exception message zetten...
        }

        return !(firstName != null ? !firstName.equals(user.getFirstName()) : user.getFirstName() != null)
                && !(lastName != null ? !lastName.equals(user.getLastName()) : user.getLastName() != null)
                && !(dob != null ? !dob.equals(userdob) : userdob != null)
                && !(email != null ? !email.equals(user.getEmail()) : user.getEmail() != null);
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " ";
    }

    public static User INVALID_USER() {
        return new NullUser();
    }
}
