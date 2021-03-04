package at.htlpinkafeld.RMA_backend_java.endpoint.authentication.token;

import at.htlpinkafeld.RMA_backend_java.exception.InvalidTokenException;

public interface TokenProcessor {
    Token validate(String tokenInQuestion) throws InvalidTokenException;
    String getUsernameFromToken(Token token);
}
