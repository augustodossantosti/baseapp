package com.development.baseapp.security.login;

import com.development.baseapp.security.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
        final String jwt = Jwts.builder()
                .setIssuer(securityProperties.getJwtIssuer())
                .setId(UUID.randomUUID().toString())
                .setSubject(authentication.getName())
                .setIssuedAt(Date.from(now))
                .setNotBefore(Date.from(now))
                .setExpiration(Date.from(now.plus(securityProperties.getJwtExpirationTime(), ChronoUnit.MINUTES)))
                .claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .signWith(Keys.hmacShaKeyFor(securityProperties.getJwtSecret().getBytes()))
                .compact();
        response.setHeader(HttpHeaders.AUTHORIZATION, jwt);
    }
}
