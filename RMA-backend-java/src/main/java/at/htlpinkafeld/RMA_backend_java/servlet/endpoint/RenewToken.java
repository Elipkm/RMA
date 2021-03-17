package at.htlpinkafeld.RMA_backend_java.servlet.endpoint;

import at.htlpinkafeld.RMA_backend_java.servlet.authentication.Secured;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.Token;
import at.htlpinkafeld.RMA_backend_java.servlet.authentication.TokenGenerator;
import at.htlpinkafeld.RMA_backend_java.utility.JWTUtils;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Secured
@Path("/renewToken")
public class RenewToken {
    @Inject
    private TokenGenerator tokenGenerator;

    @Context
    private ContainerRequestContext containerRequestContext;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response renewToken() {
        Token token = this.tokenGenerator.issueToken(JWTUtils.getLoggedInUsername(this.containerRequestContext));
        return Response.ok(token).build();
    }
}
