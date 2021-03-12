package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Credentials;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.Token;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenGenerator;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class Login {

    @Inject
    private UserDao userDao;

    @Inject
    private TokenGenerator tokenGenerator;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            this.authenticate(credentials.getUsername(), credentials.getPassword());
            Token token = this.tokenGenerator.issueToken(credentials.getUsername());
            return Response.ok(token).build();

        }catch (NotAuthorizedException forbiddenException){
            return forbiddenException.getResponse();

        } catch (DaoSysException daoSysException){
            return Response.status(500,daoSysException.getMessage()).build();
        }
    }

    private void authenticate(String username, String password) throws NotAuthorizedException, DaoSysException {
        java.util.List<User> userList = this.userDao.list();
        for(User user : userList){
            if(user.authenticate(username,password)){
                return;
            }
        }
        throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
    }

}
