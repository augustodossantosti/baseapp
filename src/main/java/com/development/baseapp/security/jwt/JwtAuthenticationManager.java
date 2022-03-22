package com.development.baseapp.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
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
    private final JwtExtractor jwtExtractor;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final Jwt jwt = jwtExtractor.extractJwt(authentication);
        jwtVerifiers.forEach(verifier -> verifier.verify(jwt));
        return new PreAuthenticatedAuthenticationToken(jwt.getSubject(), jwt.getSignature(), getAuthorities(jwt.getRoles()));
    }

    private List<GrantedAuthority> getAuthorities(final List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
