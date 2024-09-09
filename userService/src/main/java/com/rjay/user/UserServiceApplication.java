package com.rjay.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.rjay.user.entity.Admin;
import com.rjay.user.repository.AdminRepository;

import lombok.extern.slf4j.Slf4j;


@SpringBootApplication
@Slf4j
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadData(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			log.info("Starting CommandLineRunner");

			// Check if the admin user already exists
			if (adminRepository.findByEmailId("admin@example.com") == null) {
				// Create and save admin user with hardcoded data
				Admin admin = new Admin();
				admin.setEmailId("admin@rjay.com");
				admin.setPassword(passwordEncoder.encode("Admin@123")); // Encode the password
				admin.setRole("admin");
				admin.setStatus("active"); // Set status explicitly

				// Save the admin user to the database
				adminRepository.save(admin);
				log.info("Admin user created and saved to the database");
			} else {
				log.info("Admin user already exists");
			}
		};
	}

}
