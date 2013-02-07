package be.kdg.groeph.service;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.User;
import org.apache.log4j.Logger;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * To change this template use File | Settings | File Templates.
 */
@Transactional
@Service("userService")
public class UserServiceImpl  implements UserService {
    static Logger logger = Logger.getLogger(UserServiceImpl.class);

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    UserDao userDao;


    public boolean addUser(User user){
        if(userDao.getUserByEmail(user.getEmail()).isNull()){
            //todo HIER encrypten
            //siteUser.setPassword(SHAEncryption.encrypt(siteUser.getPassword()));
            getUserDao().addUser(user);
            logger.info("User " + user.getEmail() + " created");
            return true;
        }
        logger.warn("Failed to create user: " + user.getEmail());
        return false;

    }


}
