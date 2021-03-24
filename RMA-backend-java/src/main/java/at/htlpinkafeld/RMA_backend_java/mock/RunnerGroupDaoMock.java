package at.htlpinkafeld.RMA_backend_java.mock;

import at.htlpinkafeld.RMA_backend_java.dao.RunnerDao;
import at.htlpinkafeld.RMA_backend_java.dao.RunnerGroupDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.RunnerGroup;
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
