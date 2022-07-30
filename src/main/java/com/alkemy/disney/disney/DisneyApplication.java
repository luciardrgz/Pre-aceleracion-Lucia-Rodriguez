package com.alkemy.disney.disney;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class DisneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
