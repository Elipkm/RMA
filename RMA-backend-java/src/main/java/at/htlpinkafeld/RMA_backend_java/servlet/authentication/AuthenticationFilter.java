package at.htlpinkafeld.RMA_backend_java.servlet.authentication;

import io.jsonwebtoken.ExpiredJwtException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.security.Principal;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private TokenProcessor tokenProcessor;

    private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        try {
            String tokenInQuestion = this.getTokenStringFromRequest(requestContext);
            Token token = this.validateToken(tokenInQuestion);
            String username = this.getUsernameFromToken(token);
            this.identifyUserViaSecurityContext(requestContext, username);

        } catch (ExpiredJwtException e) {
            this.abortWithUnauthorized(requestContext, 440);

        } catch (InvalidAuthorizationHeaderException | InvalidTokenException exception) {
            this.abortWithUnauthorized(requestContext, Response.Status.UNAUTHORIZED.getStatusCode());
        }
    }

    private String getTokenStringFromRequest(ContainerRequestContext requestContext) throws InvalidAuthorizationHeaderException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(!isTokenBasedAuthentication(authorizationHeader)){
            throw new InvalidAuthorizationHeaderException();
        }

        return authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }
    private void abortWithUnauthorized(ContainerRequestContext requestContext, int statusCode) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(statusCode)
                        .header(HttpHeaders.WWW_AUTHENTICATE,
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private Token validateToken(String tokenInQuestion) throws InvalidTokenException, ExpiredJwtException {
        return this.tokenProcessor.validate(tokenInQuestion);
    }

    private String getUsernameFromToken(Token token){
        return this.tokenProcessor.getUsernameFromToken(token);
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
