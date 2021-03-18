package at.htlpinkafeld.RMA_backend_java.servlet;

import at.htlpinkafeld.RMA_backend_java.dao.EventDao;
import at.htlpinkafeld.RMA_backend_java.dao.EventJdbcDao;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.dao.UserJdbcDao;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenGenerator;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenJwts;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenProcessor;
import at.htlpinkafeld.RMA_backend_java.mock.EventDaoMock;
import at.htlpinkafeld.RMA_backend_java.mock.UserDaoMock;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {

       // configureMockDao();
        configureJdbcDao();

        TokenJwts tokenJwts = new TokenJwts();
        bind(tokenJwts).to(TokenGenerator.class);
        bind(tokenJwts).to(TokenProcessor.class);
    }

    private void configureJdbcDao() {
        bind(UserJdbcDao.class).to(UserDao.class);
        bind(EventJdbcDao.class).to(EventDao.class);
    }

    private void configureMockDao() {
        bind(new UserDaoMock()).to(UserDao.class);
        bind(new EventDaoMock()).to(EventDao.class);
    }
}
