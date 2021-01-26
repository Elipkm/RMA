package at.htlpinkafeld.RMA_backend_java.service;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.PossibleUser;
import at.htlpinkafeld.RMA_backend_java.pojo.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class Register {

    public Register(UserDao userDaoMock){
        this.userDao = userDaoMock;
    }
    public Register(){}
    private UserDao userDao = DependencyInjector.getUserDAO();

    @POST @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final PossibleUser possibleUser){
        try {
            User user = new User(possibleUser.getUsername(), possibleUser.getPassword());
            userDao.create(user);
        } catch (DaoSysException daoException) {
            daoException.printStackTrace();
            if(daoException.getErrorCode() == DaoSysException.UNIQUE_ERROR){
                return Response.status(409,daoException.getMessage()).build();
            }
            return Response.serverError().build();
        }

        return Response.ok().build();
    }
}
