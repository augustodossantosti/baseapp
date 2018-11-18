package com.development.baseapp.restapi.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The class <code>{@link AuthenticationInfoController}</code> is responsible for
 * providing information about <i>{@link Authentication}</i> and other objects
 * related to authentication proccess.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class AuthenticationInfoController {

    @GetMapping
    public Authentication authenticatedUser(@AuthenticationPrincipal final Authentication authentication) {
        return authentication;
    }

    @GetMapping(path = "/jwt")
    public Map<String, String> jwtInfo(final HttpServletRequest request) {
        // TODO Implementar retorno de info de jwt
        final String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        return new HashMap<>();
    }

}
