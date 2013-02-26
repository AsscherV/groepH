package be.kdg.groeph.service;

import be.kdg.groeph.model.TripUser;

public interface UserService {
    boolean addUser(TripUser user);
    boolean changePassword(TripUser user, String newPassword);
}
