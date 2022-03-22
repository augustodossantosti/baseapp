package com.development.baseapp.security.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * The exception <code>{@link JwtException}</code> indicates that a error
 * occurred during <i>JWT</i> verification.
 *
 * @author Augusto Santos
 * @version 1.0
 */
public class JwtException extends AuthenticationException {

    public JwtException(final String msg, final Throwable t) {
        super(msg, t);
    }

    public JwtException(final String msg) {
        super(msg);
    }
}
