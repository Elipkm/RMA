package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import at.htlpinkafeld.RMA_backend_java.pojo.PossibleUser;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/login")
public class Login  {

    public Login(UserDao userDaoMock){
        this.userDao = userDaoMock;
    }
    public Login(){}

    private UserDao userDao = DependencyInjector.getUserDAO();
    private PossibleUser  possibleUser;

    @POST @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final PossibleUser possibleUser){
        this.possibleUser = possibleUser;
        boolean authenticated;
        try {
            authenticated = this.authenticate();
        } catch (DaoSysException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }

        if(authenticated){
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private boolean authenticate() throws DaoSysException {
        List<User> userList = userDao.list();
        for(User user : userList){
            if(user.authenticate(possibleUser.getUsername(),possibleUser.getPassword())){
                return true;
            }
        }
        return false;
    }
}
