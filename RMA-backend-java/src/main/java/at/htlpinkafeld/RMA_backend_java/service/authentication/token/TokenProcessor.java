package at.htlpinkafeld.RMA_backend_java.service.authentication.token;

public interface TokenProcessor {
    Token validate(String tokenInQuestion) throws InvalidTokenException;
    String getUsernameFromToken(Token token);
}
