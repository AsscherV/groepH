package be.kdg.groeph.service;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.User;
import be.kdg.groeph.util.SHAEncryption;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    static Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Autowired
    UserDao userDao;

    @Override
    public User loginUser(String email, String password) {

        User user = userDao.getUserByEmail(email);
        if(user.isNull()){
            logger.warn("Login failure.");
            return User.INVALID_USER();
        } else {
            if(password.equals(user.getPassword())){
                logger.info("User " + user.getEmail() + " has logged in.");
                return user;

            } else {
                logger.warn("Login failure.");
                return User.INVALID_USER();
            }

        }
    }
}
