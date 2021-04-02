package at.htlpinkafeld.restful.dao;

import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.User;

public interface UserDao extends Crud<User> {
    User getUserByUsername(String username) throws DaoSysException;
}
