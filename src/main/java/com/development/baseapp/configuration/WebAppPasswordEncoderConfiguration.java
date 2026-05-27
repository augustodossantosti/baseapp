package com.development.baseapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The class <code>{@link WebAppPasswordEncoderConfiguration}</code> defines the
 * {@link PasswordEncoder} bean in a dedicated configuration class, separate from
 * {@link WebAppSecurityConfiguration}, to avoid circular dependency chains that
 * arise when the encoder is needed by components that are also injected into the
 * security configuration.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Configuration
public class WebAppPasswordEncoderConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
