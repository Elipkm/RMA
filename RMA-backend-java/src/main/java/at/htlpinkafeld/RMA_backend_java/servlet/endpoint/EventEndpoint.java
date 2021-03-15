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
public class EventEndpoint {

    @Inject private EventDao eventDao;

    @Inject private UserDao userDao;

    @Context private ContainerRequestContext containerRequestContext;

    private User getLoggedInUser(){
        String username = containerRequestContext.getSecurityContext().getUserPrincipal().getName();
        return userDao.getUserByUsername(username);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(final Event event){
        eventDao.create(getLoggedInUser(), event);
        JsonObject json = new JsonObject();
        json.add("id",new JsonPrimitive(event.getID()));

        return Response.ok(json.toString())
                .status(Response.Status.CREATED)
                .header("Location", "/RMA_Restful_Service/rma/event/" + event.getID())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response read(@PathParam("id") int id){
        try{
            Event event = eventDao.read(getLoggedInUser(), id);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            String response = gson.toJson(event);

            return Response.ok(response).build();

        } catch (DaoNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return Response.status(404).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(final Event event){
        try {
            eventDao.update(getLoggedInUser(), event);
            return Response.ok().build();
        } catch (DaoNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            return Response.status(404).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id){
        try {
            eventDao.delete(getLoggedInUser(), eventDao.read(getLoggedInUser(), id));
            return Response.status(200).build();

        } catch (DaoNotFoundException notFoundException){
            notFoundException.printStackTrace();
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEvents(){
        User loggedInUser = getLoggedInUser();
        List<Event> eventList = eventDao.list(loggedInUser);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String response = gson.toJson(eventList);

        return Response.ok(response).build();
    }
}
