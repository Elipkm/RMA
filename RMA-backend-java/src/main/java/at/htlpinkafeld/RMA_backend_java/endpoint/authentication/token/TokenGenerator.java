package at.htlpinkafeld.RMA_backend_java.endpoint.authentication.token;

public interface TokenGenerator {
    Token issueToken(String username);
}
