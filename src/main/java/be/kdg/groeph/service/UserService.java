package be.kdg.groeph.service;

import be.kdg.groeph.model.TripUser;

import java.sql.SQLException;

public interface UserService {
    boolean addUser(TripUser user);
    boolean changePassword(TripUser user, String newPassword);
    void updateUser(TripUser user) throws SQLException;

    TripUser getUserByEmail(String s);
}
