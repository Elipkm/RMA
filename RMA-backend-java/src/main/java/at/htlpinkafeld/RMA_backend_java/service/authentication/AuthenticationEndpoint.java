package at.htlpinkafeld.RMA_backend_java.service.authentication;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.dao.UserDao;
import at.htlpinkafeld.RMA_backend_java.pojo.Credentials;
import at.htlpinkafeld.RMA_backend_java.pojo.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;

@Path("/login")
public class AuthenticationEndpoint {

    public static final Key KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(Credentials credentials) {
        try {
            authenticate(credentials.getUsername(), credentials.getPassword());

            String token = issueToken(credentials.getUsername());

            return Response.ok(token).build();
        }catch (Exception ex){
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    private void authenticate(String username, String password) throws Exception {
        UserDao userDao = DependencyInjector.getUserDAO();
        java.util.List<User> userList = userDao.list();
        for(User user : userList){
            if(user.authenticate(username,password)){
                return;
            }
        }
        throw new Exception();
    }
    private String issueToken(String username){
        return Jwts.builder().setSubject(username).signWith(KEY).compact();
    }
}
