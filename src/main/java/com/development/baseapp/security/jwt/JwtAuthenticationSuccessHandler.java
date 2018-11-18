package com.development.baseapp.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        // TODO Implementar medida para autenticação bem sucedida.
    }
}
