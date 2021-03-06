package at.htlpinkafeld.RMA_backend_java;

import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenGenerator;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenJwts;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenProcessor;
import at.htlpinkafeld.RMA_backend_java.mock.UserDaoMock;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class UnitTestMockBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(UserDaoMock.class).to(UserDao.class);

        bind(TokenJwts.class).to(TokenGenerator.class);
        bind(TokenJwts.class).to(TokenProcessor.class);
    }
}
