package com.example.pfms;

import com.example.pfms.model.User;
import com.example.pfms.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner testUserRepository(UserRepository userRepository) {
		return args -> {
			// Insert user
			User user = new User("DEAN_ARSD", "password123", "Auditor");
			userRepository.save(user);

			// Fetch user by username
			// User fetchedUser = userRepository.findByUsername("DEAN_ARSD");
			User fetchedUser = userRepository.findByUsername("DEAN_ARSD").orElse(null);
			if (fetchedUser != null) {
				System.out.println("User fetched from DB: " + fetchedUser.getUsername() + ", Role: " + fetchedUser.getRole());
			} else {
				System.out.println("User not found!");
			}
			
			// Print to console
			if (fetchedUser != null) {
				System.out.println("User fetched from DB: " + fetchedUser.getUsername() + ", Role: " + fetchedUser.getRole());
			} else {
				System.out.println("User not found!");
			}
		};
	}
}
