package at.htlpinkafeld.RMA_backend_java.service.authentication.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;

public class TokenJwts implements TokenGenerator, TokenProcessor {
    public final Key KEY;

    public TokenJwts(Key key){
        this.KEY = key;
    }
    public TokenJwts(){
        this.KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public Token issueToken(String username){
        String token = Jwts.builder().setSubject(username).signWith(this.KEY).compact();
        return new Token(token);
    }

    @Override
    public Token validate(String token) throws InvalidTokenException {
        try {
            Jwts.parserBuilder().setSigningKey(this.KEY).build().parseClaimsJws(token);

            return new Token(token);
        }catch (InvalidTokenException exception){
            throw new InvalidTokenException();
        }
    }

    @Override
    public String getUsernameFromToken(Token token) {
        return Jwts.parserBuilder().setSigningKey(this.KEY).build()
                .parseClaimsJws(token.getToken())
                .getBody()
                .getSubject();
    }
}
