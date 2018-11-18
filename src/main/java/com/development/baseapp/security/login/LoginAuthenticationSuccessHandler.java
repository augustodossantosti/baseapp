package com.development.baseapp.security.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The class <code>{@link LoginAuthenticationSuccessHandler}</code> acts after a successful
 * login attempt.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class LoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final List<AuthenticationListener> authenticationListeners;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) throws IOException, ServletException {

        final UserFullyAuthenticatedEvent authenticatedEvent = new UserFullyAuthenticatedEvent(authentication, request, response);
        for (final AuthenticationListener listener : authenticationListeners) {
            listener.onAuthenticationSuccess(authenticatedEvent);
        }
        response.setStatus(HttpStatus.OK.value());
    }
}
