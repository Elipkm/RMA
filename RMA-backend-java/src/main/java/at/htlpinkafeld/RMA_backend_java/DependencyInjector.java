package at.htlpinkafeld.RMA_backend_java;

import at.htlpinkafeld.RMA_backend_java.dao.UserDAO;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import java.util.List;

public class DependencyInjector {
    public static UserDAO getNewUserDAO(){
        return new UserDAO() {
            @Override
            public void create(User user) {

            }

            @Override
            public User read() {
                return null;
            }

            @Override
            public void update(User user) {

            }

            @Override
            public void delete(User user) {

            }

            @Override
            public List<User> list() {
                return null;
            }
        };
    }
}
