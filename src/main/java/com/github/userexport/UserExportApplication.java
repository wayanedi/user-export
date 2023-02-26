package com.github.userexport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class UserExportApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserExportApplication.class, args);
	}

}
