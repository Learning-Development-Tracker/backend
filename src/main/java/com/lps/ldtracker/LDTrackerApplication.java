package com.lps.ldtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.lps.ldtracker"})
public class LDTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LDTrackerApplication.class, args);
	}

}
