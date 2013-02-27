package be.kdg.groeph.service;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.TripUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@Service("loginService")
public class LoginServiceImpl implements LoginService {
    static Logger logger = Logger.getLogger(LoginServiceImpl.class);


    @Qualifier("userDaoImpl")
    @Autowired
    UserDao userDao;

    @Autowired
    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager;


    @Override
    public TripUser loginUser(String email, String password) {
        TripUser user = userDao.getUserByEmail(email);
        if (user.getEmail().equals(email) && (user.getPassword().equals(password)|| user.getEmail().equals(email) && user.getTempPassword().equals(password)) ) {
            try {
                Authentication authenticate = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, user.getPassword()));
                if (authenticate.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(
                            authenticate);
                    logger.info("Login Success.");
                    logger.info("User( " + email + " ) has logged in.");
                    return userDao.getUserByEmail(email);
                }
            } catch (AuthenticationException e) {
                logger.error("AuthenticationException: " + e.getMessage());
                return TripUser.INVALID_USER();
            } catch (UnexpectedRollbackException rollback) {
                logger.error("UnexpectedRollbackException: " + rollback.getMessage());
                return TripUser.INVALID_USER();
            }
        }
        logger.warn("Login failure.");
        return TripUser.INVALID_USER();
    }
}
