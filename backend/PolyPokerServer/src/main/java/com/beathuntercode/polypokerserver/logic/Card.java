package com.beathuntercode.polypokerserver.logic;

public class Card {
    CardSuit cardSuit;
    CardNumber cardNumber;

    public Card(CardSuit cardSuit, CardNumber cardNumber) {
        this.cardSuit = cardSuit;
        this.cardNumber = cardNumber;
    }
}
