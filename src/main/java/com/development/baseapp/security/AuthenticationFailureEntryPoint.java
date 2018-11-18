package com.development.baseapp.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class <code>{@link AuthenticationFailureEntryPoint}</code> deals with
 * failure during authentication proccess.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
public class AuthenticationFailureEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(final HttpServletRequest request, final HttpServletResponse response,
                         final AuthenticationException ex) throws IOException, ServletException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "Forbidden: " + ex.getMessage());
    }
}
