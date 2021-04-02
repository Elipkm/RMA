package at.htlpinkafeld.restful.utility;

import javax.ws.rs.container.ContainerRequestContext;

public class JWTUtils {
    public static String getLoggedInUsername(ContainerRequestContext containerRequestContext){
        String username = containerRequestContext.getSecurityContext().getUserPrincipal().getName();
        return username;
    }
}
