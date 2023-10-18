package com.sidenow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ComplexBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComplexBoardApplication.class, args);
	}

}
