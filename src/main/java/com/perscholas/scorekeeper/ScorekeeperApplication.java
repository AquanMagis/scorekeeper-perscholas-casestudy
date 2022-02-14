package com.perscholas.scorekeeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ScorekeeperApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScorekeeperApplication.class, args);
	}

}
