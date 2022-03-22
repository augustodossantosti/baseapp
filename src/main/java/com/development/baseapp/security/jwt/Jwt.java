package com.development.baseapp.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Basic representation of a JWT.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@RequiredArgsConstructor
public final class Jwt {

    final Jws<Claims> jws;

    public Object getHeaderValue(final String key) {
        return getHeader().get(key);
    }

    public String getAlgorithm() {
        return getHeader().getAlgorithm();
    }

    public String getKeyId() {
        return getHeader().getKeyId();
    }

    public String getCompressionAlgorithm() {
        return getHeader().getCompressionAlgorithm();
    }

    public Object getBodyValue(final String key) {
        return getBody().get(key);
    }

    public String getIssuer() {
        return getBody().getIssuer();
    }

    public String getId() {
        return getBody().getId();
    }

    public String getSubject() {
        return getBody().getSubject();
    }

    public Date getIssuedAt() {
        return getBody().getIssuedAt();
    }

    public List<String> getRoles() {
       return getBody().get("roles", List.class);
    }

    public String getSignature() {
        return jws.getSignature();
    }

    private JwsHeader getHeader() {
        return jws.getHeader();
    }

    private Claims getBody() {
        return jws.getBody();
    }
}
