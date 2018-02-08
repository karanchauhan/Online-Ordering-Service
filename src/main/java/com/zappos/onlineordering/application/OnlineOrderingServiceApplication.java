package com.zappos.onlineordering.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.zappos")

@EnableAutoConfiguration
public class OnlineOrderingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineOrderingServiceApplication.class, args);
	}
}
