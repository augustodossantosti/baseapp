package com.development.baseapp.security.jwt;

/**
 * The interface <code>{@link JwtVerifier}</code> defines methods to be
 * implemented by objects responsible for verifing a <i>JWT</i> during
 * authentication proccess.
 *
 * @author Augusto Santos
 * @version 1.0
 */
public interface JwtVerifier {

    /**
     * Performs JWT validation.
     * @param jws the jtw.
     */
    void verify(Jwt jws) throws JwtException;
}
