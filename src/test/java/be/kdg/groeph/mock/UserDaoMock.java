package be.kdg.groeph.mock;

import be.kdg.groeph.dao.UserDao;
import be.kdg.groeph.model.TripUser;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoMock implements UserDao{
    private List<TripUser> users;
    int i = 0;

    public UserDaoMock() {
        this.users = new ArrayList<TripUser>();
    }

    @Override
    public boolean addUser(TripUser user) {
        user.setId(i);
        i++;
        users.add(user);
        return true;
    }

    @Override
    public TripUser getUserByEmail(String email) {
        TripUser user=null;
        for (TripUser tripUser : users) {
            if(tripUser.getEmail().equals(email)){
                user = tripUser;
            }
        }
        if(user == null){
            return TripUser.INVALID_USER();
        }
        else {
            return user;
        }
    }

    @Override
    public void updateUser(TripUser user) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
