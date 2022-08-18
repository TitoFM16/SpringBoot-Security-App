package com.tito.userservice;

import com.tito.userservice.domain.AppUser;
import com.tito.userservice.domain.Role;
import com.tito.userservice.services.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class UserserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserserviceApplication.class, args);
	}

/*	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/
	@Bean
	CommandLineRunner run(AppUserService userService){
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_MANAGER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));
			userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

			userService.saveUser(new AppUser(null, "John Travolta", "John","1234", new ArrayList<>() ));
			userService.saveUser(new AppUser(null, "Will Smith", "Will","1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Jim Carrey", "Jim","1234", new ArrayList<>()));
			userService.saveUser(new AppUser(null, "Arnold Schwarzenegger", "Arnold","1234", new ArrayList<>()));

			userService.addRoleToUser("John","ROLE_USER");
			userService.addRoleToUser("John","ROLE_MANAGER");
			userService.addRoleToUser("Will","ROLE_MANAGER");
			userService.addRoleToUser("Jim","ROLE_ADMIN");
			userService.addRoleToUser("Arnold","ROLE_SUPER_ADMIN");
			userService.addRoleToUser("Arnold","ROLE_ADMIN");
			userService.addRoleToUser("Arnold","ROLE_USER");

		};
	}
}
