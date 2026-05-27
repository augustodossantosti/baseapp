package com.development.baseapp.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The class <code>{@link SecurityProperties}</code> encapsulates information
 * related to authentication.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.authentication")
public class SecurityProperties {

    @NotNull private Integer jwtExpirationTime;
    @NotBlank private String jwtIssuer;
    @NotBlank private String jwtSecret;
}
