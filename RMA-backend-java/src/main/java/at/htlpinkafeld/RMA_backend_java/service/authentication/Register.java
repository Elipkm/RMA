package at.htlpinkafeld.RMA_backend_java.service.authentication;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Credentials;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.Token;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.TokenGenerator;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class Register {

    public Register(UserDao userDaoMock){
        this.userDao = userDaoMock;
    }
    public Register(){
        userDao = DependencyInjector.getUserDAO();
    }
    private final UserDao userDao;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final Credentials credentials){
        try {
            User user = new User(credentials.getUsername(), credentials.getPassword());
            userDao.create(user);

            TokenGenerator tokenGenerator = DependencyInjector.getTokenGenerator();
            Token token = tokenGenerator.issueToken(credentials.getUsername());

            return Response.ok(token).build();

        } catch (DaoSysException daoException) {
            daoException.printStackTrace();
            if(daoException.getErrorCode() == DaoSysException.UNIQUE_ERROR){
                return Response.status(409,daoException.getMessage()).build();
            }
            return Response.status(500, daoException.getMessage()).build();
        }
    }
}
