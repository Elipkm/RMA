package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usernames")
public class GetUsernames {

    private UserDao userDao = DependencyInjector.getUserDAO();

    @GET @Produces(MediaType.APPLICATION_JSON)
    public Response getUsernames(){
        String[] usernames = findUsernames();
        Gson gson = new Gson();

        return Response.ok(gson.toJson(usernames)).build();
    }

    private String[] findUsernames(){
        java.util.List<User> userList = userDao.list();

        String[] usernames = new String[userList.size()];

        for(int i = 0; i < userList.size(); i++){
            usernames[i] = userList.get(i).getUsername();
        }

        return usernames;
    }
}
