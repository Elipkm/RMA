package at.htlpinkafeld.RMA_backend_java.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("test")
public class LoggedInTest {
    @GET
    @Secured
    @Produces(MediaType.TEXT_PLAIN)
    public Response loggedInTest(@Context SecurityContext securityContext){
        Principal principal = securityContext.getUserPrincipal();
        String username = principal.getName();

        return Response.ok("Welcome " + username).build();
    }
}
