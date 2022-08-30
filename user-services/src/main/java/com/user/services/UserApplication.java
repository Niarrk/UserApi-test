package com.user.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Launch controller application
 *
 * @author : Lilian
 * @version : 1.0-SNAPSHOT
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.user.services", "com.user.business", "com.user.data"})
@EnableJpaRepositories(basePackages="com.user.data")
@EntityScan(basePackages="com.user.data")
@EnableSwagger2
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
