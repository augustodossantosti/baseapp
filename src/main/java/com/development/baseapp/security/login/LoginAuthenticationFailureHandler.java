package com.development.baseapp.security.login;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class <code>{@link LoginAuthenticationFailureHandler}</code> acts after a failed
 * login attempt.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
public class LoginAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response,
                                        final AuthenticationException ex) throws IOException, ServletException {

        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: " + ex.getMessage());
    }
}
