package com.bpolar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class BipolarHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BipolarHelperApplication.class, args);
	}

}
