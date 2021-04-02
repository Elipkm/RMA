package at.htlpinkafeld.restful.servlet.authentication;

public interface TokenGenerator {
    Token issueToken(String username);
}
