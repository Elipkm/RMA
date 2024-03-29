package at.htlpinkafeld.restful.mock;

import at.htlpinkafeld.restful.dao.UserDao;
import at.htlpinkafeld.restful.pojo.User;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDaoMock extends GenericMock<User> implements UserDao {

    public UserDaoMock() {
        super(new ArrayList<>(Arrays.asList(
                new User(1,"Elias","secret"),
                new User(2,"Daniel","secret"),
                new User(3,"Markus","secret"),
                new User(4,"Lukas","secret")
        )));
    }

    @Override
    public User getUserByUsername(String username) {
        for(User user : this.list()){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
