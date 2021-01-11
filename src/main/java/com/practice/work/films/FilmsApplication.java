package com.practice.work.films;

import com.practice.work.films.configuration.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableConfigurationProperties(ConfigProperties.class)
@EnableMongoAuditing
public class FilmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmsApplication.class, args);
    }

}
