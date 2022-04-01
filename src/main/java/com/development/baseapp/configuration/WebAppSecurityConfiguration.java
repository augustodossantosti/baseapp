package com.development.baseapp.configuration;

import com.development.baseapp.domain.user.MongoUserDetailsService;
import com.development.baseapp.security.jwt.JwtFilter;
import com.development.baseapp.security.login.LoginFilter;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * The class <code>{@link WebAppSecurityConfiguration}</code> is a <i>configuration class</i>
 * for <i>Spring Securuty</i> related functionality.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebAppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwFilter;

    @Autowired
    private LoginFilter loginFilter;

    @Autowired
    private MongoUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint failureEntryPoint;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${spring.mvc.servlet.path:}")
    private String servletPath;

    @Value("${springdoc.swagger-ui.path:}")
    private String swaggerUiPath;

    @Value("${springdoc.api-docs.path:}")
    private String apiDocsPath;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        final String BASE_PATH = servletPath + "/";
        final String LOGIN_URL = servletPath + "/login";
        final String H2_CONSOLE_URL = servletPath + "/h2-console/**";

        loginFilter.setFilterProcessesUrl(LOGIN_URL);
        jwFilter.setUrlToIgnore(LOGIN_URL, HttpMethod.POST.name(), true);

        http
            .csrf().disable()
            .headers().frameOptions().disable()
        .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, BASE_PATH).permitAll()
            .antMatchers(HttpMethod.POST,LOGIN_URL).permitAll()
            .antMatchers(HttpMethod.GET, H2_CONSOLE_URL).hasRole("ADMIN")
            .anyRequest().fullyAuthenticated()
        .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .cors().configurationSource(corsConfigurationSource())
        .and()
            .exceptionHandling().authenticationEntryPoint(failureEntryPoint)
        .and()
            .addFilterAt(jwFilter, AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
            .logout().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        final String[] SWAGGER_DOCS = {
                servletPath + "/v2/api-docs",
                servletPath + "/swagger-resources",
                servletPath + "/swagger-resources/**",
                servletPath + "/configuration/ui",
                servletPath + "/configuration/security",
                servletPath + "/webjars/**",
                servletPath + "/v3/api-docs/**",
                servletPath + swaggerUiPath,
                servletPath + swaggerUiPath + "/**",
                servletPath + apiDocsPath,
                servletPath + apiDocsPath + "/**"
        };
        final String[] API_RESOURCES = {
                servletPath + "/index.html",
                servletPath + "/resources/**"
        };
        web.ignoring().antMatchers(ArrayUtils.addAll(SWAGGER_DOCS, API_RESOURCES));
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "OPTIONS", "DELETE", "PUT"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowedHeaders(Arrays.asList("Content-Type", "Cache-Control", "Accept", "X-Requested-With", "Authorization"));
        corsConfiguration.setExposedHeaders(Collections.singletonList("Authorization"));
        final UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return configurationSource;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}
