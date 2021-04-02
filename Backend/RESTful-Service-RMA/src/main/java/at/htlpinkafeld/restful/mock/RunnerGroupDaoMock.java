package at.htlpinkafeld.restful.mock;

import at.htlpinkafeld.restful.dao.RunnerDao;
import at.htlpinkafeld.restful.dao.RunnerGroupDao;
import at.htlpinkafeld.restful.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.RunnerGroup;
import java.util.ArrayList;

public class RunnerGroupDaoMock extends GenericMock<RunnerGroup> implements RunnerGroupDao {

    public RunnerGroupDaoMock(RunnerDao runnerDao) {
        super(new ArrayList<>());
        try {
            this.create(new RunnerGroup("4BHIF",runnerDao.list()));
        } catch (DaoSysException | DaoResourceAlreadyExistsException daoSysException) {
            daoSysException.printStackTrace();
        }
    }
}
