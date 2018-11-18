package com.development.baseapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

import javax.servlet.Filter;
import javax.sql.DataSource;
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

    private final Filter jwFilter;
    private final Filter loginFilter;
    private final AuthenticationEntryPoint failureEntryPoint;
    private final DataSource dataSource;

    public WebAppSecurityConfiguration(@Qualifier("jwtFilter") final Filter jwtFilter,  @Qualifier("loginFilter") final Filter loginFilter,
                                       final AuthenticationEntryPoint failureEntryPoint, final DataSource dataSource) {
        this.jwFilter = jwtFilter;
        this.loginFilter = loginFilter;
        this.failureEntryPoint = failureEntryPoint;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/", "/index.html").permitAll()
                .antMatchers(HttpMethod.POST,"/login").permitAll()
                .antMatchers(HttpMethod.GET, "/h2-console/**").hasRole("ADMIN")
                .anyRequest().fullyAuthenticated();

        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .cors().configurationSource(corsConfigurationSource())
            .and()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .exceptionHandling().authenticationEntryPoint(failureEntryPoint);

        http
                .addFilterAt(jwFilter, AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .logout().disable();
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    @Autowired
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                    .dataSource(dataSource)
                    .passwordEncoder(passwordEncoder())
                    .rolePrefix("ROLE_")
                    .usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED FROM USERS WHERE USERNAME = ?")
                    .authoritiesByUsernameQuery("SELECT USERNAME, AUTHORITY FROM AUTHORITIES WHERE USERNAME = ?");
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
