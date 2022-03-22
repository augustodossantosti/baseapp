package com.development.baseapp.security.jwt;

import com.development.baseapp.security.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * The class <code>{@link JwtExtractor}</code> is responsible for providing an
 * instance of <code>{@link Jwt}</code> from a plain text JSON Web Token.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
@AllArgsConstructor
public class JwtExtractor {

    private final SecurityProperties securityProperties;

    public Jwt extractJwt(final Authentication authentication) {
        if (Objects.isNull(authentication.getPrincipal())) {
            throw new JwtException("Error during retrieve JWT from request.");
        }
        final String jwt = (String) authentication.getPrincipal();
        return extractJwt(jwt);
    }

    public Jwt extractJwt(final String jwt) {
        Jws<Claims> jws;
        try {
            jws = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(securityProperties.getJwtSecret().getBytes())).parseClaimsJws(jwt);
        } catch (final UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SecurityException ex) {
            throw new JwtException("Invalid JWT token: " + ex.getMessage());
        } catch (final ExpiredJwtException expiredEx) {
            throw new JwtException("JWT expired: " +  expiredEx.getMessage());
        }
        return new Jwt(jws);
    }
}
