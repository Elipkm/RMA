package at.htlpinkafeld.restful.servlet.endpoint;

import at.htlpinkafeld.restful.dao.UserDao;
import at.htlpinkafeld.restful.exception.DaoResourceAlreadyExistsException;
import at.htlpinkafeld.restful.exception.DaoSysException;
import at.htlpinkafeld.restful.pojo.Credentials;
import at.htlpinkafeld.restful.pojo.User;
import at.htlpinkafeld.restful.servlet.authentication.Token;
import at.htlpinkafeld.restful.servlet.authentication.TokenGenerator;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class RegisterEndpoint {

    @Inject
    private UserDao userDao;

    @Inject
    private TokenGenerator tokenGenerator;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final Credentials credentials){
        try {
            return this.tryRegister(credentials);

        } catch (DaoSysException daoSysException) {
            return Response.serverError().status(500, daoSysException.getMessage()).build();
        } catch (DaoResourceAlreadyExistsException daoResourceAlreadyExistsException){
            return Response.status(409,daoResourceAlreadyExistsException.getMessage()).build();
        }
    }

    private Response tryRegister(final Credentials credentials) throws DaoSysException, DaoResourceAlreadyExistsException {
        User user = new User(credentials.getUsername(), credentials.getPassword());
        this.userDao.create(user);
        Token token = this.tokenGenerator.issueToken(credentials.getUsername());

        return Response.ok(token).build();
    }
}
