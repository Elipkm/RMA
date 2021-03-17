package at.htlpinkafeld.RMA_backend_java.servlet.authentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

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
        final int expTimeInMins = 120;
        final int expTimeInMills = expTimeInMins * 60000;

        Date expDate = new Date(System.currentTimeMillis() + expTimeInMills);

        String token = Jwts.builder().setSubject(username).setExpiration(expDate).signWith(this.key).compact();
        return new Token(token);
    }

    @Override
    public Token validate(String token) throws InvalidTokenException, ExpiredJwtException {
        try {
            Jwts.parserBuilder().setSigningKey(this.key).build().parseClaimsJws(token);

            return new Token(token);
        } catch (ExpiredJwtException e) {
            throw e;
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
