package com.beathuntercode.polypokerserver.logic;

public class Player {

    Card card1;
    Card card2;
    int cash;
    boolean isReady;
    boolean isSmallBlind;
    boolean isBigBling;
    public Player(int cash) {
        this.cash = cash;
        isReady = false;
        isSmallBlind = false;
        isBigBling = false;
    }

}
