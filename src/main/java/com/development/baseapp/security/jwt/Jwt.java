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
        return getPayload().get(key);
    }

    public String getIssuer() {
        return getPayload().getIssuer();
    }

    public String getId() {
        return getPayload().getId();
    }

    public String getSubject() {
        return getPayload().getSubject();
    }

    public Date getIssuedAt() {
        return getPayload().getIssuedAt();
    }

    public List<String> getRoles() {
        return getPayload().get("roles", List.class);
    }

    public String getSignature() {
        return jws.getSignature();
    }

    private JwsHeader getHeader() {
        return jws.getHeader();
    }

    private Claims getPayload() {
        return jws.getPayload();
    }