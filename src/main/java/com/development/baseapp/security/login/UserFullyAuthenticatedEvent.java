package com.development.baseapp.security.login;

import lombok.Getter;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The class <code>{@link UserFullyAuthenticatedEvent}</code> is an <i>event</i> for
 * successful authentication and carries useful information for their handlers.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Getter
public class UserFullyAuthenticatedEvent extends AbstractAuthenticationEvent {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public UserFullyAuthenticatedEvent(final Authentication authentication, final HttpServletRequest request,
                                       final HttpServletResponse response) {
        super(authentication);
        this.request = request;
        this.response = response;
    }
}
