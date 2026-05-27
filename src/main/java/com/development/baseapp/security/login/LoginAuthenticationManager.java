package com.development.baseapp.security.login;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * The class <code>{@link LoginAuthenticationManager}</code> is responsible for
 * authenticating users via JDBC during the login process.
 *
 * Separated from {@link com.development.baseapp.configuration.WebAppSecurityConfiguration}
 * to avoid a circular dependency:
 * WebAppSecurityConfiguration → LoginFilter → LoginAuthenticationManager.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Primary
@Component
public class LoginAuthenticationManager implements AuthenticationManager {

    private final ProviderManager delegate;

    public LoginAuthenticationManager(final DataSource dataSource, final PasswordEncoder passwordEncoder) {
        final JdbcDaoImpl userDetailsService = new JdbcDaoImpl();
        userDetailsService.setDataSource(dataSource);
        userDetailsService.setUsersByUsernameQuery(
                "SELECT USERNAME, PASSWORD, ENABLED FROM USERS WHERE USERNAME = ?");
        userDetailsService.setAuthoritiesByUsernameQuery(
                "SELECT USERNAME, AUTHORITY FROM AUTHORITIES WHERE USERNAME = ?");
        userDetailsService.setRolePrefix("ROLE_");
        userDetailsService.afterPropertiesSet();

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        this.delegate = new ProviderManager(provider);
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        return delegate.authenticate(authentication);
    }
}
