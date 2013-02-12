package be.kdg.groeph.service;

import be.kdg.groeph.model.User;

/**
 * To change this template use File | Settings | File Templates.
 */
public interface LoginService {
    User loginUser(String email, String password);
}
