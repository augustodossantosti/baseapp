package com.development.baseapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * The class <code>{@link WebAppMvcConfiguration}</code> is a <i>configuration class</i>
 * for <i>Spring MVC</i> related functionality.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Configuration
public class WebAppMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        registry.addViewController("").setViewName("index.html");
        registry.addViewController("/").setViewName("index.html");
    }
}
