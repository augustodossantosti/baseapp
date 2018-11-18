package com.development.baseapp.security.login;

import java.io.IOException;

/**
 * The interface <code>{@link AuthenticationListener}</code> defines some operations
 * to be execute after a successful authentication.
 *
 * @author Augusto Santos
 * @version 1.0
 */
public interface AuthenticationListener {

    /**
     * Action to be execute after successful authentication.
     *
     * @param authenticationEvent Authentication event.
     * @throws IOException I/O error that may happen during execution, for example writting the
     * <code>{@link java.io.OutputStream}</code> of <code>{@link javax.servlet.http.HttpServletResponse}</code>.
     */
    void onAuthenticationSuccess(final UserFullyAuthenticatedEvent authenticationEvent) throws IOException;
}
