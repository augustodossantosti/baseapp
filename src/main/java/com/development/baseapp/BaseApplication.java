package com.development.baseapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The class <code>{@link BaseApplication}</code> is responsible for
 * start the application execution.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }
}
