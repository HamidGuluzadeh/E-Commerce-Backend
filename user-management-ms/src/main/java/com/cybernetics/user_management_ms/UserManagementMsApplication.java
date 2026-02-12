package com.cybernetics.user_management_ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UserManagementMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementMsApplication.class, args);
	}

}
