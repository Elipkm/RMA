package at.htlpinkafeld.restful.servlet.endpoint;

import at.htlpinkafeld.restful.dao.RunnerDao;
import at.htlpinkafeld.restful.exception.DaoNotFoundException;
import at.htlpinkafeld.restful.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.Runner;
import javax.inject.Inject;
import javax.ws.rs.Path;
import java.util.List;

@Path("/runner")
public class RunnerEndpoint extends CrudEndpoint<Runner> {

    @Inject private RunnerDao runnerDao;

    @Override
    public String getPath() {
        return "/runner";
    }

    @Override
    public void createService(Runner runner) throws DaoSysException, DaoResourceAlreadyExistsException {
        runnerDao.create(runner);
    }

    @Override
    public Runner readService(int id) throws DaoSysException, DaoNotFoundException {
        return runnerDao.read(id);
    }

    @Override
    public void updateService(Runner runner) throws DaoSysException, DaoNotFoundException {
        runnerDao.update(runner);
    }

    @Override
    public void deleteService(int id) throws DaoSysException, DaoNotFoundException {
        Runner runnerToDelete = runnerDao.read(id);
        runnerDao.delete(runnerToDelete);
    }

    @Override
    public List<Runner> listService() throws DaoSysException {
        return runnerDao.list();
    }
}
