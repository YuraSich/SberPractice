package com.example.cds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
public class CdsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdsApplication.class, args);
	}

}
