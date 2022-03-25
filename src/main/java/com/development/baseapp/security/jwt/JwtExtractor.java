package com.development.baseapp.security.jwt;

import com.development.baseapp.security.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
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
    private static final String HEADER_PREFIX = "Bearer ";

    private final SecurityProperties securityProperties;

    public Jwt extractJwt(final Authentication authentication) {
        verifyNull(authentication.getPrincipal());
        final String bearerToken = (String) authentication.getPrincipal();
        return extractJwt(bearerToken);
    }

    public Jwt extractJwt(final String bearerToken) {
        verifyNull(bearerToken);
        final String jwt = removePrefix(bearerToken);
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

    private String removePrefix(final String bearerToken) {
        return Objects.isNull(bearerToken) ? null : bearerToken.substring(HEADER_PREFIX.length());
    }

    private void verifyNull(final Object obj) {
        if (Objects.isNull(obj)) {
            throw new JwtException("Error during retrieve JWT from request.");
        }
    }
}
