package be.kdg.groeph.dao;

import be.kdg.groeph.model.TripUser;

public interface UserDao {
    boolean addUser(TripUser user);

    TripUser getUserByEmail(String email);

}