package at.htlpinkafeld.RMA_backend_java.servlet.authentication;

public interface TokenGenerator {
    Token issueToken(String username);
}
