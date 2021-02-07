package at.htlpinkafeld.RMA_backend_java.service.authentication;

import at.htlpinkafeld.RMA_backend_java.DependencyInjector;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.InvalidTokenException;
import at.htlpinkafeld.RMA_backend_java.service.authentication.token.Token;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(!isTokenBasedAuthentication(authorizationHeader)){
            abortWithUnauthorized(requestContext);
            return;
        }

        String tokenInQuestion = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            Token token = validateToken(tokenInQuestion);

            String username = getUsernameFromToken(token);
            identifyUserViaSecurityContext(requestContext, username);

        } catch (InvalidTokenException invalidTokenException) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }
    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }
    private Token validateToken(String tokenInQuestion) throws InvalidTokenException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
        // if tokenInQuestion is valid a token object is returned
        return DependencyInjector.getTokenProcessor().validate(tokenInQuestion);
    }

    private String getUsernameFromToken(Token token){
        return DependencyInjector.getTokenProcessor().getUsernameFromToken(token);
    }
    private void identifyUserViaSecurityContext(ContainerRequestContext requestContext,String username){
        final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> username;
            }

            @Override
            public boolean isUserInRole(String role) {
                return true;
            }

            @Override
            public boolean isSecure() {
                return currentSecurityContext.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return AUTHENTICATION_SCHEME;
            }
        });
    }
}
