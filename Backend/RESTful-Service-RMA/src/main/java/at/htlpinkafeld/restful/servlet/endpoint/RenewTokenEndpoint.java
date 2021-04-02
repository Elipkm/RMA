package at.htlpinkafeld.restful.servlet.endpoint;

import at.htlpinkafeld.restful.servlet.authentication.Secured;
import at.htlpinkafeld.restful.servlet.authentication.Token;
import at.htlpinkafeld.restful.servlet.authentication.TokenGenerator;
import at.htlpinkafeld.restful.utility.JWTUtils;

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
public class RenewTokenEndpoint {
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
