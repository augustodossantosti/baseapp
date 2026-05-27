package com.development.baseapp.security.login;

import com.development.baseapp.security.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The class <code>{@link JwtWriterListener}</code> is responsible for write and put a <i>JWT</i>
 * on response's <i>Authorization Header</i>.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class JwtWriterListener implements AuthenticationListener {

    private final SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(final UserFullyAuthenticatedEvent authenticationEvent) throws IOException {
        final HttpServletResponse response = authenticationEvent.getResponse();
        final Authentication authentication = authenticationEvent.getAuthentication();
        final Instant now = Instant.now();
        final SecretKey signingKey = Keys.hmacShaKeyFor(securityProperties.getJwtSecret().getBytes());
        final String jwt = Jwts.builder()
                .issuer(securityProperties.getJwtIssuer())
                .id(UUID.randomUUID().toString())
                .subject(authentication.getName())
                .issuedAt(Date.from(now))
                .notBefore(Date.from(now))
                .expiration(Date.from(now.plus(securityProperties.getJwtExpirationTime(), ChronoUnit.MINUTES)))
                .claim("roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .signWith(signingKey)
                .compact();
        response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
    }
}
