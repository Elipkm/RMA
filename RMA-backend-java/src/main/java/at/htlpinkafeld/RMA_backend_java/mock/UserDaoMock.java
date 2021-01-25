package at.htlpinkafeld.RMA_backend_java.mock;

import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoMock implements UserDao {
    private List<User> userList = new ArrayList<>(Arrays.asList(
            new User("Elias","secret",1),
            new User("Daniel","secret",2),
            new User("Markus","empire",3),
            new User("Lukas","wembley",4)
            ));
    @Override
    public void create(User user) {
        userList.add(user);
    }

    @Override
    public User read(int id) {
        return userList.get(id-1);
    }

    @Override
    public void update(User user) {
        userList.set(user.getID()-1,user);
    }

    @Override
    public void delete(User user) {
        userList.remove(user);
    }

    @Override
    public List<User> list() {
        return userList;
    }
}
