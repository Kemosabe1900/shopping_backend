package com.example.CLproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
@EnableJpaRepositories("com.example.CLproject.daos")
@EntityScan("com.example.CLproject.models")
public class CLprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CLprojectApplication.class, args);
	}

}
