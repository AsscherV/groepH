package be.kdg.groeph.service;

import be.kdg.groeph.model.TripUser;

public interface LoginService {
    TripUser loginUser(String email, String password);
}
