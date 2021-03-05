package at.htlpinkafeld.RMA_backend_java.servlet.authentication;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class TokenJwts implements TokenGenerator, TokenProcessor {
    private final Key key;

    public TokenJwts(Key key){
        this.key = key;
    }
    public TokenJwts(){
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public Token issueToken(String username){
        String token = Jwts.builder().setSubject(username).signWith(this.key).compact();
        return new Token(token);
    }

    @Override
    public Token validate(String token) throws InvalidTokenException {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);

            return new Token(token);
        }catch (Exception exception){
            throw (InvalidTokenException) new InvalidTokenException().initCause(exception);
        }
    }

    @Override
    public String getUsernameFromToken(Token token) {
        return Jwts.parserBuilder().setSigningKey(this.key).build()
                .parseClaimsJws(token.getToken())
                .getBody()
                .getSubject();
    }
}
