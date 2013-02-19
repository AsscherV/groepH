package be.kdg.groeph.service;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.TripUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Transactional
@Service("userService")
public class UserServiceImpl  implements UserService, UserDetailsService {
    static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    UserDao userDao;

    public boolean addUser(TripUser user){
        TripUser userByEmail = userDao.getUserByEmail(user.getEmail());
        if(userByEmail.isNull()){
            userDao.addUser(user);
            logger.info("User " + user.getEmail() + " created");
            return  true;
        }
        logger.warn("Failed to create user: " + user.getEmail());
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
        boolean accountNonExpired = true;
        boolean accountNonLocked = true;
        boolean credentialsNonExpired = true;
        boolean enabled = true;
        User user = new User("test", "test", enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,getDefaultAuthority());

        TripUser userDB = userDao.getUserByEmail(email);

        if(userDB.getRole().equals("ROLE_ADMIN")){
            user = new User(userDB.getEmail(), userDB.getPassword(),
                    userDB.isEnabled(),userDB.isAccountNonExpired(), userDB.isCredentialsNonExpired(), userDB.isAccountNonLocked(), getAdminAuthority());
        }  else if(userDB.getRole().equals("ROLE_USER")){
            user = new User(userDB.getEmail(), userDB.getPassword(),
                    userDB.isEnabled(),userDB.isAccountNonExpired(), userDB.isCredentialsNonExpired(), userDB.isAccountNonLocked(),getDefaultAuthority());
        }
        return user;
    }

    private Collection<GrantedAuthority> getDefaultAuthority() {
        Collection<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
        userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return userAuthorities;
    }
    private Collection<GrantedAuthority> getAdminAuthority() {
        Collection<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
        userAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return userAuthorities;
    }


}
