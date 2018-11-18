package com.development.baseapp.security.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * The class <code>{@link LoginFilter}</code> acts as a <i>Spring Security</i> filter
 * and is responsible for handle requests for login proccess.
 *
 * @author Augusto Santos
 * @version 1.0
 *
 * @see javax.servlet.Filter
 */
@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    @Autowired
    public void setAuthenticationSuccessHandler(@Qualifier("loginAuthenticationSuccessHandler") final AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }

    @Override
    @Autowired
    public void setAuthenticationFailureHandler(@Qualifier("loginAuthenticationFailureHandler") final AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(@Qualifier("authenticationManagerBean") final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

}
