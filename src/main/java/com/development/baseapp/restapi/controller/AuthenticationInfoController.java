package com.development.baseapp.restapi.controller;

import com.development.baseapp.security.jwt.Jwt;
import com.development.baseapp.security.jwt.JwtExtractor;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * The class <code>{@link AuthenticationInfoController}</code> is responsible for
 * providing information about <i>{@link Authentication}</i> and other objects
 * related to authentication proccess.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class AuthenticationInfoController {

    private final JwtExtractor jwtExtractor;

    @GetMapping
    public Authentication authenticatedUser(final Authentication authentication) {
        return authentication;
    }

    @GetMapping(path = "/jwt")
    public Jwt jwtInfo(final HttpServletRequest request) {
        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return jwtExtractor.extractJwt(bearerToken);
    }

}
