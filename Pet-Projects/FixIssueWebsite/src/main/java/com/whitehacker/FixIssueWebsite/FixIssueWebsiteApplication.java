package com.whitehacker.FixIssueWebsite;

import com.whitehacker.FixIssueWebsite.model.User;
import com.whitehacker.FixIssueWebsite.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FixIssueWebsiteApplication {
	@Autowired
	BCryptPasswordEncoder pwEncoder;
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(FixIssueWebsiteApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			User moderator = new User("moderator", pwEncoder.encode("test"), "muslim.whitehacker@gmail.com", "Vafo", "Tagoyev", "Yoshlik street, 125/3", "992006671", "Jizzakh");
			moderator.setRole("ROLE_MODERATOR");
			userService.save(moderator);
		};
	}
}
