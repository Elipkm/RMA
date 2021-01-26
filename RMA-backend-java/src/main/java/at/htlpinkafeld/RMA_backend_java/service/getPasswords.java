package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/getPW")
public class getPasswords {

    @GET
    public Response getPW(){
        Gson gson = new Gson();
        return Response.ok(gson.toJson(passwords())).build();
    }
    private String[] passwords(){
        java.util.List<User> userList;
        try {
            userList = DependencyInjector.getUserDAO().list();
        } catch (DaoSysException e) {
            e.printStackTrace();
            userList = new ArrayList<>(0);
        }

        String[] usernames = new String[userList.size()];

        for(int i = 0; i < userList.size(); i++){
            usernames[i] = userList.get(i).getEncodedPassword();
        }

        return usernames;
    }
}
