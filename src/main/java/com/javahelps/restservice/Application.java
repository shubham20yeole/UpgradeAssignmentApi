package com.javahelps.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableJpaRepositories
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
