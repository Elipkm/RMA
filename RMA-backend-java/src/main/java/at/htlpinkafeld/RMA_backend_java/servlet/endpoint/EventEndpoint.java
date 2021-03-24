package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.dao.EventDao;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoNotFoundException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.servlet.ApplicationBinder;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.Secured;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Secured
@Path("/event")
public class EventEndpoint extends CrudEndpoint<Event> {

    @Inject private EventDao eventDao;

    @Inject private UserDao userDao;

    @Context private ContainerRequestContext containerRequestContext;

    private User getLoggedInUser() throws DaoSysException {
        String username = containerRequestContext.getSecurityContext().getUserPrincipal().getName();
        return userDao.getUserByUsername(username);
    }

    @Override
    public String getPath() {
        return "/event";
    }

    @Override
    public void createService(Event event) throws DaoSysException {
        eventDao.create(getLoggedInUser(), event);
    }

    @Override
    public Event readService(int id) throws DaoSysException, DaoNotFoundException {
        return eventDao.read(getLoggedInUser(), id);
    }

    @Override
    public void updateService(Event event) throws DaoSysException, DaoNotFoundException {
        eventDao.update(getLoggedInUser(), event);
    }

    @Override
    public void deleteService(int id) throws DaoSysException, DaoNotFoundException {
        User loggedInUser = getLoggedInUser();
        Event toDelete = eventDao.read(loggedInUser, id);
        eventDao.delete(loggedInUser, toDelete);
    }

    @Override
    public List<Event> listService() throws DaoSysException {
        User loggedInUser = getLoggedInUser();
        return eventDao.list(loggedInUser);
    }
}
