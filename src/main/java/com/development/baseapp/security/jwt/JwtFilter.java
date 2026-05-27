package com.development.baseapp.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

/**
 * The class <code>{@link JwtFilter}</code> acts as a <i>Spring Security's</i> filter
 * and is responsible for handling requests for protected resources, ensuring that
 * a valid <i>JWT</i> would be present in the related request.
 *
 * @author Augusto Santos
 * @version 1.0
 *
 * @see jakarta.servlet.Filter
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
     * Excludes a specific URL pattern from filter verification
     *
     * @param pattern
     * @param httpMethod
     */
    public void setUrlToIgnore(final String pattern, final HttpMethod httpMethod) {
        final RequestMatcher requestMatcher = PathPatternRequestMatcher.pathPattern(httpMethod, pattern);
        final RequestMatcher negatedRequestMatcher = new NegatedRequestMatcher(requestMatcher);
        setRequiresAuthenticationRequestMatcher(negatedRequestMatcher);
    }
}
