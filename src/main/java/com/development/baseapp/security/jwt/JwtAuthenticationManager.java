package com.development.baseapp.security.jwt;

import com.development.baseapp.security.SecurityProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The class <code>{@link JwtAuthenticationManager}</code> is responsible for checking
 * whether a <i>JWT</i> that came via <I>Authentication Header</I> is valid or not,
 * retrieving a {@link Authentication} object.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {

    private final List<JwtVerifier> jwtVerifiers;
    private final SecurityProperties securityProperties;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if (Objects.isNull(authentication.getPrincipal())) {
            throw new JwtException("Error during retrieve JWT from request.");
        }

        final String jwt = (String) authentication.getPrincipal();
        Jws<Claims> jws;
        try {
            jws = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(securityProperties.getJwtSecret().getBytes())).parseClaimsJws(jwt);
        } catch (final UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SecurityException ex) {
            throw new JwtException("Invalid JWT token: " + ex.getMessage());
        } catch (final ExpiredJwtException expiredEx) {
            throw new JwtException("JWT expired: " +  expiredEx.getMessage());
        }
        jwtVerifiers.forEach(verifier -> verifier.verify(jws));
        final List<String> roles = jws.getBody().get("roles", List.class);
        final List<GrantedAuthority> authorities = getAuthority(roles);
        return new PreAuthenticatedAuthenticationToken(jws.getBody().getSubject(), null, authorities);
    }

    private List<GrantedAuthority> getAuthority(final List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
