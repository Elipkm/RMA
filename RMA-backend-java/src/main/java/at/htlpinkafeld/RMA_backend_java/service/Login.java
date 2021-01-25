package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/login")
public class Login  {

    private UserDao userDAO = DependencyInjector.getUserDAO();

    @POST @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final User possibleUser){
        boolean authenticated = this.authenticate(possibleUser);

        if(authenticated){
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private boolean authenticate(final User possibleUser){
        List<User> userList = userDAO.list();
        for(User user : userList){
            if(possibleUser.equals(user)){
                return true;
            }
        }
        return false;
    }
}
