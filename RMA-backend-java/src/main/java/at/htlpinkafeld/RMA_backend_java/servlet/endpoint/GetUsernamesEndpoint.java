package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/usernames")
public class GetUsernamesEndpoint {

    @Inject
    private UserDao userDao;

    @GET @Produces(MediaType.APPLICATION_JSON)
    public Response getUsernames(){
        String[] usernames = findUsernames();
        return Response.ok(usernames).build();
    }

    private String[] findUsernames(){
        List<User> userList = getUserList();
        String[] usernames = new String[userList.size()];

        for(int i = 0; i < userList.size(); i++){
            usernames[i] = userList.get(i).getUsername();
        }

        return usernames;
    }
    private List<User> getUserList(){
        try {
            return userDao.list();
        } catch (DaoSysException e) {
            e.printStackTrace();
            return new ArrayList<>(0);
        }
    }
}
