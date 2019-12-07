package com.javahelps.restservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.javahelps.restservice.entity.User;
import com.javahelps.restservice.repository.UserRepository;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@EnableJpaRepositories
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

//	@Bean
//	protected CommandLineRunner init(final UserRepository userRepository) {
//
//		return args -> {
//			User user = new User();
//			user.setFullname("Administrator");
//			user.setEmail("admin@javahelps.com");
//			userRepository.save(user);
//
//		};
//	}
}
