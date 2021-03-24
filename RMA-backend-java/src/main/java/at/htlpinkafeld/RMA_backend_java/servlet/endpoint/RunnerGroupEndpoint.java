package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.dao.RunnerGroupDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.RunnerGroup;

import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.List;

@Path("/runnerGroup")
public class RunnerGroupEndpoint extends CrudEndpoint<RunnerGroup> {

    @Inject private RunnerGroupDao runnerGroupDao;

    @Override
    public String getPath() {
        return "/runnerGroup";
    }

    @Override
    public void createService(RunnerGroup entity) throws DaoSysException, DaoResourceAlreadyExistsException {
        runnerGroupDao.create(entity);
    }

    @Override
    public RunnerGroup readService(int id) throws DaoSysException, DaoNotFoundException {
        return runnerGroupDao.read(id);
    }

    @Override
    public void updateService(RunnerGroup entity) throws DaoSysException, DaoNotFoundException {
        runnerGroupDao.update(entity);
    }

    @Override
    public void deleteService(int id) throws DaoSysException, DaoNotFoundException {
        runnerGroupDao.delete(runnerGroupDao.read(id));
    }

    @Override
    public List<RunnerGroup> listService() throws DaoSysException {
        return runnerGroupDao.list();
    }
}
