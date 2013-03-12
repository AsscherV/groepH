package be.kdg.groeph.service;

import be.kdg.groeph.bean.TripBean;
import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;

import java.util.ArrayList;

public interface MailService {
    @Deprecated
    public void uponSuccessfulRegistration(String email);

    public boolean isSuccessfulRegistration(String email);
    public boolean recoverPassword(String email);
    public boolean uponFacebookLoginNoAccount(String email, String password);
    public boolean uponTripInvitationNewUser (String email, Trip trip, String newPassword);
    public boolean uponTripInvitation (String email, Trip trip);

}
