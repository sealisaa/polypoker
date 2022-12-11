package com.beathuntercode.polypokerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.beathuntercode.polypokerserver")
public class PolyPokerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PolyPokerServerApplication.class, args);
	}

}
