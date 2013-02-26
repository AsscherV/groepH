package be.kdg.groeph.service;

public interface MailService {
    public void uponSuccessfulRegistration(String email);
    public boolean recoverPassword(String email);
}
