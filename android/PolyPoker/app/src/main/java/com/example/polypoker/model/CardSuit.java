package com.example.polypoker.model;

public enum CardSuit {
    HEARTS(1),
    DIAMONDS(2),
    CLUBS(3),
    SPADES(4);

    public final int value;

    CardSuit(int value) {
        this.value = value;
    }
}
