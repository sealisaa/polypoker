package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.beathuntercode.polypokerserver.database.controller.RoomController;

public class Utilities {

    public static RoomsController roomsController = new RoomsController();

    public static ArrayList<Card> cardList = new ArrayList<>(List.of(
            new Card(CardSuit.HEARTS, CardNumber.TWO),
            new Card(CardSuit.HEARTS, CardNumber.THREE),
            new Card(CardSuit.HEARTS, CardNumber.FOUR),
            new Card(CardSuit.HEARTS, CardNumber.FIVE),
            new Card(CardSuit.HEARTS, CardNumber.SIX),
            new Card(CardSuit.HEARTS, CardNumber.SEVEN),
            new Card(CardSuit.HEARTS, CardNumber.EIGHT),
            new Card(CardSuit.HEARTS, CardNumber.NINE),
            new Card(CardSuit.HEARTS, CardNumber.TEN),
            new Card(CardSuit.HEARTS, CardNumber.JACK),
            new Card(CardSuit.HEARTS, CardNumber.QUEEN),
            new Card(CardSuit.HEARTS, CardNumber.KING),
            new Card(CardSuit.HEARTS, CardNumber.ACE),

            new Card(CardSuit.DIAMONDS, CardNumber.TWO),
            new Card(CardSuit.DIAMONDS, CardNumber.THREE),
            new Card(CardSuit.DIAMONDS, CardNumber.FOUR),
            new Card(CardSuit.DIAMONDS, CardNumber.FIVE),
            new Card(CardSuit.DIAMONDS, CardNumber.SIX),
            new Card(CardSuit.DIAMONDS, CardNumber.SEVEN),
            new Card(CardSuit.DIAMONDS, CardNumber.EIGHT),
            new Card(CardSuit.DIAMONDS, CardNumber.NINE),
            new Card(CardSuit.DIAMONDS, CardNumber.TEN),
            new Card(CardSuit.DIAMONDS, CardNumber.JACK),
            new Card(CardSuit.DIAMONDS, CardNumber.QUEEN),
            new Card(CardSuit.DIAMONDS, CardNumber.KING),
            new Card(CardSuit.DIAMONDS, CardNumber.ACE),

            new Card(CardSuit.CLUBS, CardNumber.TWO),
            new Card(CardSuit.CLUBS, CardNumber.THREE),
            new Card(CardSuit.CLUBS, CardNumber.FOUR),
            new Card(CardSuit.CLUBS, CardNumber.FIVE),
            new Card(CardSuit.CLUBS, CardNumber.SIX),
            new Card(CardSuit.CLUBS, CardNumber.SEVEN),
            new Card(CardSuit.CLUBS, CardNumber.EIGHT),
            new Card(CardSuit.CLUBS, CardNumber.NINE),
            new Card(CardSuit.CLUBS, CardNumber.TEN),
            new Card(CardSuit.CLUBS, CardNumber.JACK),
            new Card(CardSuit.CLUBS, CardNumber.QUEEN),
            new Card(CardSuit.CLUBS, CardNumber.KING),
            new Card(CardSuit.CLUBS, CardNumber.ACE) ,

            new Card(CardSuit.SPADES, CardNumber.TWO),
            new Card(CardSuit.SPADES, CardNumber.THREE),
            new Card(CardSuit.SPADES, CardNumber.FOUR),
            new Card(CardSuit.SPADES, CardNumber.FIVE),
            new Card(CardSuit.SPADES, CardNumber.SIX),
            new Card(CardSuit.SPADES, CardNumber.SEVEN),
            new Card(CardSuit.SPADES, CardNumber.EIGHT),
            new Card(CardSuit.SPADES, CardNumber.NINE),
            new Card(CardSuit.SPADES, CardNumber.TEN),
            new Card(CardSuit.SPADES, CardNumber.JACK),
            new Card(CardSuit.SPADES, CardNumber.QUEEN),
            new Card(CardSuit.SPADES, CardNumber.KING),
            new Card(CardSuit.SPADES, CardNumber.ACE)
    ));

    public static int getRndIntInRange(int min, int max){
        return (int) (Math.random()*((max-min)+1))+min;
    }
}
