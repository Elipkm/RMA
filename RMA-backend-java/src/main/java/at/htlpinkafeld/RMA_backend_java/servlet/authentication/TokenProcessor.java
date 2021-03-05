package at.htlpinkafeld.RMA_backend_java.servlet.authentication;

public interface TokenProcessor {
    Token validate(String tokenInQuestion) throws InvalidTokenException;
    String getUsernameFromToken(Token token);
}
