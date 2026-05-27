package com.development.baseapp.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class <code>{@link JwtAuthenticationSuccessHandler}</code> is responsible for handle a successful
 * user authentication on URIs that requires an <i>Authorization Header</i> and a valid <i>JWT</i>.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        // JWT authentication success is intentionally a no-op here.
        // The validated Authentication object is set in the SecurityContext by the
        // AbstractPreAuthenticatedProcessingFilter before this handler is called.
        // Add any post-authentication logic here if needed (e.g. audit logging).
    }
}
