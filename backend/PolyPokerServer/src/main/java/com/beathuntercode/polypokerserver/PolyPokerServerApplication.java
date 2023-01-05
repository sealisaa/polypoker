package com.beathuntercode.polypokerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beathuntercode.polypokerserver.logic.Utilities;

@SpringBootApplication(scanBasePackages = "com.beathuntercode.polypokerserver")
public class PolyPokerServerApplication {

	public static void main(String[] args) {

		Utilities.roomsController.createRoom(11111, 10, 20);

		SpringApplication.run(PolyPokerServerApplication.class, args);

	}

}
