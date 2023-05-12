package com.kata.tennis;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Tennis extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Tennis.class);
        app.setDefaultProperties(Collections
          .singletonMap("server.port", "9080"));
        app.run(args);
		System.out.println(".........Application Started...........");
	}

}
