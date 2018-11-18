package com.development.baseapp.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

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

    @NotBlank private String urlSuccessLogout;
    @NotBlank private Integer jwtExpirationTime;
    @NotBlank private String jwtIssuer;
    @NotBlank private String jwtSecret;
}
