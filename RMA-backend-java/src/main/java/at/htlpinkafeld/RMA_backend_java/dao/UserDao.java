package at.htlpinkafeld.RMA_backend_java.dao;

import at.htlpinkafeld.RMA_backend_java.pojo.User;

public interface UserDao extends Crud<User> {
    User getUserByUsername(String username);
}
