package com.example.EmployWise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmployWiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployWiseApplication.class, args);
	}

}
