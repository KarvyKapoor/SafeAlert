package com.emergency.response_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.emergency.response_system.repository")
@SpringBootApplication(scanBasePackages = "com.emergency.response_system")
public class ResponseSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResponseSystemApplication.class, args);
	}

}
