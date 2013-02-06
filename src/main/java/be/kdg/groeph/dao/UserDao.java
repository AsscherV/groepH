package be.kdg.groeph.dao;

import be.kdg.groeph.model.User;

/**
 * To change this template use File | Settings | File Templates.
 */
public interface UserDao {
    void addUser(User user);

    User getUserByEmail(String email);
}