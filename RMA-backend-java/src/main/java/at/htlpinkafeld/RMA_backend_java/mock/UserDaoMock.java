package at.htlpinkafeld.RMA_backend_java.mock;

import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoMock implements UserDao {

    public UserDaoMock(){}

    private List<User> userList = new ArrayList<>(Arrays.asList(
            new User("Elias","secret"),
            new User("Daniel","secret"),
            new User("Markus","empire"),
            new User("Lukas","wembley")
            ));

    private boolean isUnique(User user){
        for(User u : userList){
            if(u.getUsername().equals(user.getUsername())){
                return false;
            }
        }
        return true;
    }
    @Override
    public void create(User user) throws DaoSysException {
        if(isUnique(user)) {
            userList.add(user);
        }else{
            throw new DaoSysException("Username not unique", DaoSysException.UNIQUE_ERROR);
        }
    }

    @Override
    public User read(int id) {
        return userList.get(id-1);
    }

    @Override
    public void update(User user) {
        if(!isUnique(user)) {
            userList.set(user.getID() - 1, user);
        }
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
