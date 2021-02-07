package at.htlpinkafeld.RMA_backend_java;

import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.mock.UserDaoMock;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.TokenGenerator;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.TokenJwts;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.TokenProcessor;

public class DependencyInjector {
    private static UserDao userDaoMock = new UserDaoMock();
    public static UserDao getUserDAO(){
        //return new UserJdbcDao(); //For using real mysql server config
        return new UserDaoMock();
    }

    private static TokenJwts tokenJwts = new TokenJwts();
    public static TokenGenerator getTokenGenerator(){
        return new TokenJwts();
    }
    public static TokenProcessor getTokenProcessor(){return new TokenJwts();}
}