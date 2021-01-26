package at.htlpinkafeld.RMA_backend_java;

import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.dao.UserJdbcDao;
import at.htlpinkafeld.RMA_backend_java.mock.UserDaoMock;

public class DependencyInjector {
    public static UserDao getUserDAO(){
        //return new UserJdbcDao(); //For using real mysql server config
        return new UserDaoMock();
    }
}