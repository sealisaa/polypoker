package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    public static RoomsController roomsController = new RoomsController();

    public static ArrayList<Card> cardList = new ArrayList<>(List.of(
            new Card(SUIT.HEARTS, RANK.TWO),
            new Card(SUIT.HEARTS, RANK.THREE),
            new Card(SUIT.HEARTS, RANK.FOUR),
            new Card(SUIT.HEARTS, RANK.FIVE),
            new Card(SUIT.HEARTS, RANK.SIX),
            new Card(SUIT.HEARTS, RANK.SEVEN),
            new Card(SUIT.HEARTS, RANK.EIGHT),
            new Card(SUIT.HEARTS, RANK.NINE),
            new Card(SUIT.HEARTS, RANK.TEN),
            new Card(SUIT.HEARTS, RANK.JACK),
            new Card(SUIT.HEARTS, RANK.QUEEN),
            new Card(SUIT.HEARTS, RANK.KING),
            new Card(SUIT.HEARTS, RANK.ACE),

            new Card(SUIT.DIAMONDS, RANK.TWO),
            new Card(SUIT.DIAMONDS, RANK.THREE),
            new Card(SUIT.DIAMONDS, RANK.FOUR),
            new Card(SUIT.DIAMONDS, RANK.FIVE),
            new Card(SUIT.DIAMONDS, RANK.SIX),
            new Card(SUIT.DIAMONDS, RANK.SEVEN),
            new Card(SUIT.DIAMONDS, RANK.EIGHT),
            new Card(SUIT.DIAMONDS, RANK.NINE),
            new Card(SUIT.DIAMONDS, RANK.TEN),
            new Card(SUIT.DIAMONDS, RANK.JACK),
            new Card(SUIT.DIAMONDS, RANK.QUEEN),
            new Card(SUIT.DIAMONDS, RANK.KING),
            new Card(SUIT.DIAMONDS, RANK.ACE),

            new Card(SUIT.CLUBS, RANK.TWO),
            new Card(SUIT.CLUBS, RANK.THREE),
            new Card(SUIT.CLUBS, RANK.FOUR),
            new Card(SUIT.CLUBS, RANK.FIVE),
            new Card(SUIT.CLUBS, RANK.SIX),
            new Card(SUIT.CLUBS, RANK.SEVEN),
            new Card(SUIT.CLUBS, RANK.EIGHT),
            new Card(SUIT.CLUBS, RANK.NINE),
            new Card(SUIT.CLUBS, RANK.TEN),
            new Card(SUIT.CLUBS, RANK.JACK),
            new Card(SUIT.CLUBS, RANK.QUEEN),
            new Card(SUIT.CLUBS, RANK.KING),
            new Card(SUIT.CLUBS, RANK.ACE) ,

            new Card(SUIT.SPADES, RANK.TWO),
            new Card(SUIT.SPADES, RANK.THREE),
            new Card(SUIT.SPADES, RANK.FOUR),
            new Card(SUIT.SPADES, RANK.FIVE),
            new Card(SUIT.SPADES, RANK.SIX),
            new Card(SUIT.SPADES, RANK.SEVEN),
            new Card(SUIT.SPADES, RANK.EIGHT),
            new Card(SUIT.SPADES, RANK.NINE),
            new Card(SUIT.SPADES, RANK.TEN),
            new Card(SUIT.SPADES, RANK.JACK),
            new Card(SUIT.SPADES, RANK.QUEEN),
            new Card(SUIT.SPADES, RANK.KING),
            new Card(SUIT.SPADES, RANK.ACE)
    ));

    public static int getRndIntInRange(int min, int max){
        return (int) (Math.random()*((max-min)+1))+min;
    }
}
