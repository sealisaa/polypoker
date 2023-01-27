package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    public static RoomsController roomsController = new RoomsController();

    public static ArrayList<Card> cardList = new ArrayList<>(List.of(
            new Card(Suit.HEARTS, Rank.TWO),
            new Card(Suit.HEARTS, Rank.THREE),
            new Card(Suit.HEARTS, Rank.FOUR),
            new Card(Suit.HEARTS, Rank.FIVE),
            new Card(Suit.HEARTS, Rank.SIX),
            new Card(Suit.HEARTS, Rank.SEVEN),
            new Card(Suit.HEARTS, Rank.EIGHT),
            new Card(Suit.HEARTS, Rank.NINE),
            new Card(Suit.HEARTS, Rank.TEN),
            new Card(Suit.HEARTS, Rank.JACK),
            new Card(Suit.HEARTS, Rank.QUEEN),
            new Card(Suit.HEARTS, Rank.KING),
            new Card(Suit.HEARTS, Rank.ACE),

            new Card(Suit.DIAMONDS, Rank.TWO),
            new Card(Suit.DIAMONDS, Rank.THREE),
            new Card(Suit.DIAMONDS, Rank.FOUR),
            new Card(Suit.DIAMONDS, Rank.FIVE),
            new Card(Suit.DIAMONDS, Rank.SIX),
            new Card(Suit.DIAMONDS, Rank.SEVEN),
            new Card(Suit.DIAMONDS, Rank.EIGHT),
            new Card(Suit.DIAMONDS, Rank.NINE),
            new Card(Suit.DIAMONDS, Rank.TEN),
            new Card(Suit.DIAMONDS, Rank.JACK),
            new Card(Suit.DIAMONDS, Rank.QUEEN),
            new Card(Suit.DIAMONDS, Rank.KING),
            new Card(Suit.DIAMONDS, Rank.ACE),

            new Card(Suit.CLUBS, Rank.TWO),
            new Card(Suit.CLUBS, Rank.THREE),
            new Card(Suit.CLUBS, Rank.FOUR),
            new Card(Suit.CLUBS, Rank.FIVE),
            new Card(Suit.CLUBS, Rank.SIX),
            new Card(Suit.CLUBS, Rank.SEVEN),
            new Card(Suit.CLUBS, Rank.EIGHT),
            new Card(Suit.CLUBS, Rank.NINE),
            new Card(Suit.CLUBS, Rank.TEN),
            new Card(Suit.CLUBS, Rank.JACK),
            new Card(Suit.CLUBS, Rank.QUEEN),
            new Card(Suit.CLUBS, Rank.KING),
            new Card(Suit.CLUBS, Rank.ACE) ,

            new Card(Suit.SPADES, Rank.TWO),
            new Card(Suit.SPADES, Rank.THREE),
            new Card(Suit.SPADES, Rank.FOUR),
            new Card(Suit.SPADES, Rank.FIVE),
            new Card(Suit.SPADES, Rank.SIX),
            new Card(Suit.SPADES, Rank.SEVEN),
            new Card(Suit.SPADES, Rank.EIGHT),
            new Card(Suit.SPADES, Rank.NINE),
            new Card(Suit.SPADES, Rank.TEN),
            new Card(Suit.SPADES, Rank.JACK),
            new Card(Suit.SPADES, Rank.QUEEN),
            new Card(Suit.SPADES, Rank.KING),
            new Card(Suit.SPADES, Rank.ACE)
    ));

    public static int getRndIntInRange(int min, int max){
        return (int) (Math.random()*((max-min)+1))+min;
    }
}
