package at.htlpinkafeld.restful.servlet;

import at.htlpinkafeld.restful.dao.*;
import at.htlpinkafeld.restful.mock.RunnerDaoMock;
import at.htlpinkafeld.restful.mock.RunnerGroupDaoMock;
import at.htlpinkafeld.restful.servlet.authentication.TokenGenerator;
import at.htlpinkafeld.restful.servlet.authentication.TokenJwts;
import at.htlpinkafeld.restful.servlet.authentication.TokenProcessor;
import at.htlpinkafeld.restful.mock.EventDaoMock;
import at.htlpinkafeld.restful.mock.UserDaoMock;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

    private boolean configureMock;
    public ApplicationBinder(boolean configureMock){
        this.configureMock = configureMock;
    }
    @Override
    protected void configure() {
        if(configureMock){
            this.configureMockDao();
        } else {
            this.configureJdbcDao();
        }

        TokenJwts tokenJwts = new TokenJwts();
        bind(tokenJwts).to(TokenGenerator.class);
        bind(tokenJwts).to(TokenProcessor.class);
    }

    private void configureJdbcDao() {
        bind(UserJdbcDao.class).to(UserDao.class);
        bind(EventJdbcDao.class).to(EventDao.class);
    }

    private void configureMockDao() {
        UserDaoMock userDaoMock = new UserDaoMock();
        EventDaoMock eventDaoMock = new EventDaoMock();
        RunnerDaoMock runnerDaoMock = new RunnerDaoMock();
        RunnerGroupDaoMock runnerGroupDaoMock = new RunnerGroupDaoMock(runnerDaoMock);

        bind(userDaoMock).to(UserDao.class);
        bind(eventDaoMock).to(EventDao.class);
        bind(runnerDaoMock).to(RunnerDao.class);
        bind(runnerGroupDaoMock).to(RunnerGroupDao.class);

    }
}
