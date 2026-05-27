package com.development.baseapp.security.jwt;

import com.development.baseapp.security.SecurityProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link JwtExtractor}.
 */
@ExtendWith(MockitoExtension.class)
class JwtExtractorTest {

    private static final String SECRET = "test-secret-key-that-is-long-enough-for-hmac";

    @Mock
    private SecurityProperties securityProperties;

    @InjectMocks
    private JwtExtractor jwtExtractor;

    @Test
    void shouldThrowWhenBearerTokenIsNull() {
        final JwtException ex = assertThrows(JwtException.class,
                () -> jwtExtractor.extractJwt((String) null));
        assertTrue(ex.getMessage().contains("Error during retrieve JWT"));
    }

    @Test
    void shouldThrowWhenAuthenticationPrincipalIsNull() {
        final var auth = new PreAuthenticatedAuthenticationToken(null, null);
        final JwtException ex = assertThrows(JwtException.class,
                () -> jwtExtractor.extractJwt(auth));
        assertTrue(ex.getMessage().contains("Error during retrieve JWT"));
    }

    @Test
    void shouldThrowWhenTokenIsExpired() {
        when(securityProperties.getJwtSecret()).thenReturn(SECRET);
        final String expiredToken = buildToken(SECRET, Instant.now().minus(10, ChronoUnit.MINUTES));
        final JwtException ex = assertThrows(JwtException.class,
                () -> jwtExtractor.extractJwt("Bearer " + expiredToken));
        assertTrue(ex.getMessage().contains("JWT expired"));
    }

    @Test
    void shouldExtractValidJwt() {
        when(securityProperties.getJwtSecret()).thenReturn(SECRET);
        final String token = buildToken(SECRET, Instant.now().plus(30, ChronoUnit.MINUTES));
        final Jwt jwt = jwtExtractor.extractJwt("Bearer " + token);
        assertNotNull(jwt);
        assertEquals("test-user", jwt.getSubject());
        assertEquals("test-issuer", jwt.getIssuer());
        assertNotNull(jwt.getRoles());
        assertTrue(jwt.getRoles().contains("ROLE_USER"));
    }

    @Test
    void shouldThrowWhenTokenSignatureIsInvalid() {
        when(securityProperties.getJwtSecret()).thenReturn(SECRET);
        final String tokenWithWrongSecret = buildToken("another-secret-key-that-is-long-enough!!", Instant.now().plus(30, ChronoUnit.MINUTES));
        final JwtException ex = assertThrows(JwtException.class,
                () -> jwtExtractor.extractJwt("Bearer " + tokenWithWrongSecret));
        assertTrue(ex.getMessage().contains("Invalid JWT token"));
    }

    private String buildToken(final String secret, final Instant expiration) {
        final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        final Instant now = Instant.now();
        return Jwts.builder()
                .issuer("test-issuer")
                .subject("test-user")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .claim("rol