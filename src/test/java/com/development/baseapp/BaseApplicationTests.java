package com.development.baseapp;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The class <code>{@link BaseApplicationTests}</code> is responsible for
 * execute application's tests.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class BaseApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        Assert.assertNotNull(context);
    }

}
