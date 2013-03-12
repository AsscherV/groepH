package be.kdg.groeph.dao;

import be.kdg.groeph.model.Trip;
import be.kdg.groeph.model.TripUser;

import java.sql.SQLException;

public interface UserDao {
    boolean addUser(TripUser user);

    TripUser getUserByEmail(String email);

    void addInvitedUser(TripUser tripUser);

    void updateUser(TripUser user) throws SQLException;
}