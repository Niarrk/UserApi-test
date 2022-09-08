package com.user.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Launch controller application
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.user.controller", "com.user.business", "com.user.data"})
@EnableJpaRepositories(basePackages = "com.user.data")
@EntityScan(basePackages = "com.user.data")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
