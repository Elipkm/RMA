package at.htlpinkafeld.RMA_backend_java.service.authentication;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.exception.DaoSysException;
import at.htlpinkafeld.RMA_backend_java.pojo.Credentials;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.Token;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.TokenGenerator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class AuthenticationEndpoint {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            authenticate(credentials.getUsername(), credentials.getPassword());

            TokenGenerator tokenGenerator = DependencyInjector.getTokenGenerator();
            Token token = tokenGenerator.issueToken(credentials.getUsername());

            return Response.ok(token).build();

        }catch (ForbiddenException forbiddenException){
            return Response.status(Response.Status.FORBIDDEN).build();

        } catch (DaoSysException daoSysException){
            return Response.status(500,daoSysException.getMessage()).build();
        }
    }

    private void authenticate(String username, String password) throws ForbiddenException, DaoSysException {
        final UserDao userDao = DependencyInjector.getUserDAO();
        java.util.List<User> userList = userDao.list();
        for(User user : userList){
            if(user.authenticate(username,password)){
                return;
            }
        }
        throw new ForbiddenException();
    }

}
