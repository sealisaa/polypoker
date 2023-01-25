package com.beathuntercode.polypokerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beathuntercode.polypokerserver.logic.Player;
import com.beathuntercode.polypokerserver.logic.Utilities;

@SpringBootApplication(scanBasePackages = "com.beathuntercode.polypokerserver")
public class PolyPokerServerApplication {

	public static void main(String[] args) {

		initRoomForTest();

		SpringApplication.run(PolyPokerServerApplication.class, args);

	}


	private static void initRoomForTest() {
		Utilities.roomsController.createRoom(1, 10, 20);
	}
}
