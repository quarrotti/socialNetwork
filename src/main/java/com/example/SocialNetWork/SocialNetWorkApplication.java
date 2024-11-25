package com.example.SocialNetWork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SocialNetWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetWorkApplication.class, args);
	}

}
