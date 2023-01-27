package com.beathuntercode.polypokerserver.websocket;

public enum BetType {
    SMALL_BLIND(1),
    BIG_BLIND(2),
    BET(3),
    RAISE(4),
    CHECK(5),
    FOLD(6);


    private int value;

    BetType(int value) {
        this.value = value;
    }
}
