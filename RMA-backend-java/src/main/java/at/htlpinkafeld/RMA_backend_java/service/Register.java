package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class Register {

    private UserDao userDao = DependencyInjector.getUserDAO();

    @POST @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final User user){
        userDao.create(user);
        // TODO send Authentication

        return Response.ok().build();
    }
}
