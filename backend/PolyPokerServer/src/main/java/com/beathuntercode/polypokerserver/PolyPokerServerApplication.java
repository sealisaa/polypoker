package com.beathuntercode.polypokerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beathuntercode.polypokerserver.logic.Player;
import com.beathuntercode.polypokerserver.logic.Room;
import com.beathuntercode.polypokerserver.logic.Utilities;

@SpringBootApplication(scanBasePackages = "com.beathuntercode.polypokerserver")
public class PolyPokerServerApplication {

	public static void main(String[] args) {

		initRoomForTest();

		SpringApplication.run(PolyPokerServerApplication.class, args);

	}

	private static void initRoomForTest() {
		Utilities.roomsController.createRoom(1, 10, 20);

		Room room = Utilities.roomsController.roomsMap.get(1);
		for (int i = 0; i < 5; i++) {
			room.getGameManager().getFaceUp().add(room.getGameManager().dealRandomCard());
		}

		Player player = new Player("a", "Sh Sh", 0, 0);
		player.setCard1(room.getGameManager().dealRandomCard());
		player.setCard2(room.getGameManager().dealRandomCard());

		room.getPlayersMap().put(player.getLogin(), player);
	}
}
