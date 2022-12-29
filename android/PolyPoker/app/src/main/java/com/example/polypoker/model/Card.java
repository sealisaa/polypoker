package com.example.polypoker.model;

public class Card {
    CardSuit cardSuit;
    CardNumber cardNumber;

    public Card(CardSuit cardSuit, CardNumber cardNumber) {
        this.cardSuit = cardSuit;
        this.cardNumber = cardNumber;
    }
}
