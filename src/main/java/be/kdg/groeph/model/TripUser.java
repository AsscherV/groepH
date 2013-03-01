package be.kdg.groeph.model;

import be.kdg.groeph.model.Null.NullUser;
import be.kdg.groeph.model.Null.Nullable;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.util.JSONPObject;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="t_user")
public class TripUser implements Nullable, Serializable {
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
    @Column(name="gender", nullable = false, length = 100)
    private char gender;
    @Column(name="email", nullable = false, length = 100)
    private String email;
    @Column(name="password", nullable = false, length = 100)
    private String password;
    @Column(name="oldPassword", nullable = true, length = 100)
    private String tempPassword;
    @Column(name="role", nullable = false, length = 100)
    private String role;
    @Column(name="dateRegistered", nullable = false, length = 100)
    private Date dateRegistered;
    //@Column(name="isAdmin", nullable = false)
    //private Boolean isAdmin;

    @ManyToOne
    @JoinColumn(name = "address")
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Address address;

    @Column(name="accountNonExpired", nullable = true)
    private boolean accountNonExpired;
    @Column(name="credentialsNonExpired", nullable = true)
    private boolean credentialsNonExpired;
    @Column(name="enabled", nullable = true)
    private boolean enabled;
    @Column(name="accountNonLocked", nullable = true)
    private boolean accountNonLocked;

    @OneToMany(mappedBy = "tripUser", fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<Trip> trips;

    @ManyToMany
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name="id", nullable = true)
    private List<Trip> invitedTrips = new ArrayList<Trip>();


    public TripUser() {
    }

    public TripUser(String firstName, String lastName, Date dateOfBirth, String phoneNumber, char gender, String email, String password, Address address, Date dateRegistered, String role) {
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
        trips = new ArrayList<Trip>();
    }

    public TripUser(String firstName, String lastName, Date dateOfBirth, String phoneNumber, char gender, String email, String password, String tempPassword, Address address, Date dateRegistered, String role) {
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
        this.tempPassword = tempPassword;
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

    public List<Trip> getInvitedTrips() {
        return invitedTrips;
    }

    public void setInvitedTrips(List<Trip> invitedTrips) {
        this.invitedTrips = invitedTrips;
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

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public boolean isAdmin(){
        if(role.equals("ROLE_ADMIN")){
            return  true;
        }
        return false;
    }
    public List<Trip> getTrips() {
        return trips;
    }

    public String getTempPassword() {
        return tempPassword;
    }
    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public void addTrip(Trip trip){
        trip.setTripUser(this);
        trips.add(trip);
    }

    public void addInvitedTrip(Trip trip){
        invitedTrips.add(trip);
    }
    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
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

        TripUser user = (TripUser) obj;
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

    public static TripUser INVALID_USER() {
        return new NullUser();
    }
}
