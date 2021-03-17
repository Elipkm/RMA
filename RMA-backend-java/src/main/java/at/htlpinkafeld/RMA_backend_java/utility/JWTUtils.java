package at.htlpinkafeld.RMA_backend_java.utility;

import at.htlpinkafeld.RMA_backend_java.pojo.User;

import javax.ws.rs.container.ContainerRequestContext;

public class JWTUtils {
    public static String getLoggedInUsername(ContainerRequestContext containerRequestContext){
        String username = containerRequestContext.getSecurityContext().getUserPrincipal().getName();
        return username;
    }
}
