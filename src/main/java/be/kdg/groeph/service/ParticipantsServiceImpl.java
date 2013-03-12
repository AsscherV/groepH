package be.kdg.groeph.service;

import be.kdg.groeph.dao.TripDao;
import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.Address;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;
import be.kdg.groeph.util.RandomPassword;
import be.kdg.groeph.util.SHAEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;

@Transactional
@Service("participantsService")
public class ParticipantsServiceImpl implements ParticipantsService {
    private TripUser tripUser;

    @Autowired
    UserService userService;

    @Autowired
    TripService tripService;

    @Autowired
    MailService mailService;

    @Override
    public void addUsersToTrip(ArrayList<String> validEmails, Trip trip) {

        for (int i = 0; i < validEmails.size(); i++) {
            String email = validEmails.get(i);
            tripUser = userService.getUserByEmail(email);
            if(!tripUser.isNull()) {
                setUserAndTrip(tripUser, trip);
                mailService.uponTripInvitation(tripUser.getEmail(), trip);
            } else{
                tripUser =  makeNewUserWithGeneratedPass(email, trip);
                setUserAndTrip(tripUser, trip);
            }
        }
    }

    private TripUser makeNewUserWithGeneratedPass(String email, Trip trip) {
        String newPassword = RandomPassword.generatePassword();

        Address address = new Address("no streetname", "no streetnumber", "no zip", "no Hometown");
        tripUser = new TripUser("no Firstname", "no Lastname", new Date(), "no phonenumber", 'M', email, SHAEncryption.encrypt(newPassword), address, new Date(), "ROLE_USER");
        tripUser.setAccountNonExpired(true);
        tripUser.setAccountNonLocked(true);
        tripUser.setCredentialsNonExpired(true);
        tripUser.setEnabled(true);
        userService.addUser(tripUser);
        mailService.uponTripInvitationNewUser(email, trip, newPassword);
        return tripUser;
    }

    public void setUserAndTrip(TripUser tripUser, Trip trip){
        trip.addTripUser(tripUser);
        tripService.addUserToTrip(trip);
    }
}
