package com.development.baseapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The class <code>{@link BaseApplicationTests}</code> is responsible for
 * executing application's tests.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@SpringBootTest
class BaseApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        assertNotNull(context);
    }
}
