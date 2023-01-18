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

		Utilities.roomsController.roomsMap.get(1).getPlayersMap().put(
				"a",
				new Player(
						"a",
						"Sh Sh",
						0,
						4534
				)
		);
		Utilities.roomsController.roomsMap.get(1).getPlayersMap().put(
				"a",
				new Player(
						"b",
						"Sha Sha",
						0,
						4134
				)
		);
		Utilities.roomsController.roomsMap.get(1).getPlayersMap().put(
				"a",
				new Player(
						"b",
						"Sha Sha",
						0,
						2313
				)
		);

		for (Player player : Utilities.roomsController.roomsMap.get(1).getPlayersMap().values()) {
			player.setReady(true);
		}
	}
}
