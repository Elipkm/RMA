package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.dao.EventDao;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.Secured;
import at.htlpinkafeld.RMA_backend_java.pojo.Event;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import com.google.gson.Gson;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Secured
@Path("/events")
public class EventEndpoint {

    @Inject private EventDao eventDao;

    @Inject private UserDao userDao;

    @Context private ContainerRequestContext containerRequestContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(){
        String username = containerRequestContext.getSecurityContext().getUserPrincipal().getName();
        User loggedInUser = userDao.getUserByUsername(username);
        List<Event> eventList = eventDao.list(loggedInUser);

        Gson gson = new Gson();
        String response = gson.toJson(eventList);

        return Response.ok(response).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEvent(){
        return Response.ok().build();
    }
}
