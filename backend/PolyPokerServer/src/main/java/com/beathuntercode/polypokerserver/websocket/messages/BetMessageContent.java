package com.beathuntercode.polypokerserver.websocket.messages;

public class BetMessageContent extends MessageContent {

    private int betValue;

    public BetMessageContent(int roomCode, int betValue) {
        super(roomCode);
        this.betValue = betValue;
    }

    public int getBetValue() {
        return betValue;
    }

    public void setBetValue(int betValue) {
        this.betValue = betValue;
    }
}
