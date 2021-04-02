package at.htlpinkafeld.restful.servlet.authentication;

public interface TokenProcessor {
    Token validate(String tokenInQuestion) throws InvalidTokenException;
    String getUsernameFromToken(Token token);
}
