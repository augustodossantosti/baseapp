package com.development.baseapp.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The class <code>{@link JwtAuthenticationFailureHandler}</code> is responsible for handle failed
 * authentication attempt on URIs that requires an <i>Authorization Header</i> and a valid <i>JWT</i>.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse,
                                        final AuthenticationException ex) throws IOException, ServletException {

        httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized: " + ex.getMessage());
    }

}
