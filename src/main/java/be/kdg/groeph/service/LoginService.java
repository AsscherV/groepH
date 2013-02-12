package be.kdg.groeph.service;

import be.kdg.groeph.model.TripUser;

/**
 * To change this template use File | Settings | File Templates.
 */
public interface LoginService {
    TripUser loginUser(String email, String password);
}
