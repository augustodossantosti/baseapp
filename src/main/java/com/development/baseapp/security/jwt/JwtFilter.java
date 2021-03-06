package com.development.baseapp.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * The class <code>{@link JwtFilter}</code> acts as a <i>Spring Security's</i> filter
 * and is responsible for handling requests for protected resources, ensuring that
 * a valid <i>JWT</i> would be present in the related request.
 *
 * @author Augusto Santos
 * @version 1.0
 *
 * @see javax.servlet.Filter
 */
@Component
public class JwtFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String HEADER_PREFIX = "Bearer ";

    public JwtFilter(@Qualifier("jwtAuthenticationManager") final AuthenticationManager jwAuthenticationManager) {
        setAuthenticationManager(jwAuthenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(final HttpServletRequest httpServletRequest) {
        return extractJwt(httpServletRequest);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(final HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    @Autowired
    public void setAuthenticationSuccessHandler(@Qualifier("jwtAuthenticationSuccessHandler") final AuthenticationSuccessHandler authenticationSuccessHandler) {
        super.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

    @Override
    @Autowired
    public void setAuthenticationFailureHandler(@Qualifier("jwtAuthenticationFailureHandler") final AuthenticationFailureHandler authenticationFailureHandler) {
        super.setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    private String extractJwt(final HttpServletRequest request) {
        final String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
        return Objects.isNull(jwt) ? null : jwt.substring(HEADER_PREFIX.length());
    }
}
