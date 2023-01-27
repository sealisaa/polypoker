package com.beathuntercode.polypokerserver.logic;

public enum Suit {
    HEARTS(1),
    DIAMONDS(2),
    CLUBS(3),
    SPADES(4);

    public final int value;

    Suit(int value) {
        this.value = value;
    }
}
