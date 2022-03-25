package com.development.baseapp.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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

    public JwtFilter(@Qualifier("jwtAuthenticationManager") final AuthenticationManager jwAuthenticationManager) {
        setAuthenticationManager(jwAuthenticationManager);
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(final HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
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

    /**
     * Excludes a specif URL pathern from filter verification
     *
     * @param pattern
     * @param httpMethod
     * @param caseSensitive
     */
    public void setUrlToIgnore(final String pattern, final String httpMethod, final boolean caseSensitive) {
        final RequestMatcher requestMatcher = new AntPathRequestMatcher(pattern, httpMethod, caseSensitive);
        final RequestMatcher negatedRequestMatcher = new NegatedRequestMatcher(requestMatcher);
        setRequiresAuthenticationRequestMatcher(negatedRequestMatcher);
    }
}
