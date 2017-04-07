package by.haidash.blog.server.config.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by haidash on 04.04.17.
 */
@Component
public class TokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${authentication.header}")
    public String authorizationHeader;

    @Value("${authentication.token.secret}")
    private String secret;

    @Value("${authentication.token.expiration}")
    private final long expiration = 604800;

    public String getUsernameFromToken(final String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Date getExpirationDateFromToken(final String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(final String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(final AuthenticationUser authenticationUser) {
        final Map<String, Object> claims = new HashMap<>();
        final String username = authenticationUser.getUsername();
        final String email = authenticationUser.getEmail();
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_USERNAME, username != null ? username : email);
        return generateToken(claims);
    }

    private String generateToken(final Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(final String token) {
        return !isTokenExpired(token);
    }

    public String refreshToken(final String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final AuthenticationUser user = (AuthenticationUser) userDetails;
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getUsername()) || username.equals(user.getEmail())) && !isTokenExpired(token);
    }
}
