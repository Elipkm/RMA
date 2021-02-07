package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import at.htlpinkafeld.RMA_backend_java.service.authentication.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/usernames")
public class GetUsernames {

    private final UserDao userDao = DependencyInjector.getUserDAO();

    @GET @Produces(MediaType.APPLICATION_JSON)
    public Response getUsernames(){
        String[] usernames = findUsernames();
       // Gson gson = new Gson();

        return Response.ok(usernames).build();
    }

    private String[] findUsernames(){
        java.util.List<User> userList;
        try {
            userList = userDao.list();
        } catch (DaoSysException e) {
            e.printStackTrace();
            userList = new ArrayList<>(0);
        }

        String[] usernames = new String[userList.size()];

        for(int i = 0; i < userList.size(); i++){
            usernames[i] = userList.get(i).getUsername();
        }

        return usernames;
    }
}
