package com.cloudoptix.cloudoptix_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CloudoptixBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudoptixBackendApplication.class, args);
	}

}
