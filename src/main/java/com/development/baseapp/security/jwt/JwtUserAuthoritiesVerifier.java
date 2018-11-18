package com.development.baseapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
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
    public void verify(Jws<Claims> jws) {
        final List roles = jws.getBody().get("roles", List.class);
        if (CollectionUtils.isEmpty(roles)) {
            throw new JwtException("Invalid Authorities.");
        }
    }
}
