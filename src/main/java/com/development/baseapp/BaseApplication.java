package com.development.baseapp;

import com.development.baseapp.domain.entity.Domain;
import com.development.baseapp.domain.user.Authority;
import com.development.baseapp.domain.user.Role;
import com.development.baseapp.domain.user.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The class <code>{@link BaseApplication}</code> is responsible for
 * start the application execution.
 *
 * @author Augusto Santos
 * @version 1.0
 */
@Log4j2
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

    @Bean
    @Profile("test")
    public CommandLineRunner setupDatabase(final MongoTemplate mongoTemplate) {
        return args -> {
            log.info("Generating sample data");
            mongoTemplate.dropCollection(User.class);
            mongoTemplate.insert(new User("user", "$2a$05$Bkk.jx7rJXlH2UfTn0srNeZhM0NJYIUz.VcRoLpj/iv4YcMr.luua",
                    true, List.of(new Authority(Role.USER, LocalDateTime.now(), null)),
                    LocalDateTime.now(), null, null, "user@user.com"));
            mongoTemplate.insert(new User("admin", "$2a$05$TmR52.JmG0ePZ4QNWdVU.eWsLdqHc2ZJxxrz384sDZtIqw99evsu6",
                    true, List.of(new Authority(Role.ADMIN, LocalDateTime.now(), null)),
                    LocalDateTime.now(), null, null, "admin@admin.com"));
            mongoTemplate.dropCollection(Domain.class);
            mongoTemplate.insert(new Domain("INFO1"));
            mongoTemplate.insert(new Domain("INFO2"));
            log.info("Sample data created");
        };
    }
}
