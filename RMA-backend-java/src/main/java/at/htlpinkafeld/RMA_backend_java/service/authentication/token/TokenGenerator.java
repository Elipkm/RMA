package at.htlpinkafeld.RMA_backend_java.service.authentication.token;

public interface TokenGenerator {
    Token issueToken(String username);
}
