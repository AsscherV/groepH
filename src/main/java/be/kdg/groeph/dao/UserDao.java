package be.kdg.groeph.dao;

import be.kdg.groeph.model.TripUser;

/**
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
    boolean addUser(TripUser user);

    TripUser getUserByEmail(String email);

}