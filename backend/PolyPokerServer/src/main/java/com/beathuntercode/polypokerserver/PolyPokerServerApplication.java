package com.beathuntercode.polypokerserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.HandRank;
import com.beathuntercode.polypokerserver.logic.Player;
import com.beathuntercode.polypokerserver.logic.Rank;
import com.beathuntercode.polypokerserver.logic.Room;
import com.beathuntercode.polypokerserver.logic.Suit;
import com.beathuntercode.polypokerserver.logic.Utilities;
import com.beathuntercode.polypokerserver.logic.handEvaluation.HandRanker;
import com.beathuntercode.polypokerserver.logic.handEvaluation.PokerHand;

@SpringBootApplication(scanBasePackages = "com.beathuntercode.polypokerserver")
public class PolyPokerServerApplication {

	public static void main(String[] args) {

//		initRoomForTest();
//		Room room = Utilities.roomsController.roomsMap.get(1);
//		testHandEvaluation(room, room.getPlayersMap().get("a"));

		Utilities.roomsController.createRoom(1, 10, 20);
		SpringApplication.run(PolyPokerServerApplication.class, args);

	}

	private static void initRoomForTest() {
		Utilities.roomsController.createRoom(1, 10, 20);

		Room room = Utilities.roomsController.roomsMap.get(1);
		for (int i = 0; i < 5; i++) {
			room.getGameManager().getFaceUp().add(room.getGameManager().dealRandomCard());
		}

		addPlayer(room, "a", "Sh Sh", 0, 0);

	}

	private static void testHandEvaluation(Room room, Player player) {
		List<Card> cardsList = new ArrayList<>();

		cardsList.add(new Card(Suit.SPADES, Rank.KING));
		cardsList.add(new Card(Suit.DIAMONDS, Rank.FIVE));
		cardsList.add(new Card(Suit.HEARTS, Rank.EIGHT));
		cardsList.add(new Card(Suit.HEARTS, Rank.SIX));
		cardsList.add(new Card(Suit.SPADES, Rank.FOUR));
		cardsList.add(new Card(Suit.SPADES, Rank.JACK));
		cardsList.add(new Card(Suit.DIAMONDS, Rank.THREE));

		PokerHand pokerHand = HandRanker.getInstance().getRank(cardsList);

		System.out.println(pokerHand.toString());
	}

	private static void addPlayer(Room room, String login, String name, int currentStake, int cash) {
		Player player = new Player(login, name, currentStake, cash);
		player.setCard1(room.getGameManager().dealRandomCard());
		player.setCard2(room.getGameManager().dealRandomCard());
		room.getPlayersMap().put(player.getLogin(), player);
	}
}
