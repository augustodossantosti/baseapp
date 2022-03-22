package com.development.baseapp.security.jwt;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * The class <code>{@link JwtUserAuthoritiesVerifier}</code> is responsible for
 * ensure that user credentials inside <i>JWT</i> are valid.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
public class JwtUserAuthoritiesVerifier implements JwtVerifier {

    @Override
    public void verify(Jwt jws) {
        final List<String> roles = jws.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            throw new JwtException("Invalid Authorities.");
        }
    }
}
