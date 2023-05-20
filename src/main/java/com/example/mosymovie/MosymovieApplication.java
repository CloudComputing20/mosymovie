package com.example.mosymovie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MosymovieApplication {

	public static void main(String[] args) {
		SpringApplication.run(MosymovieApplication.class, args);
	}

}
