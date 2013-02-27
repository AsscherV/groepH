package be.kdg.groeph.dao;

import be.kdg.groeph.model.TripUser;

import java.sql.SQLException;

public interface UserDao {
    boolean addUser(TripUser user);

    TripUser getUserByEmail(String email);


    void updateUser(TripUser user) throws SQLException;
}