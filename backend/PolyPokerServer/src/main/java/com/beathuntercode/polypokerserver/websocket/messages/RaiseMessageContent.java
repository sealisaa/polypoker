package com.beathuntercode.polypokerserver.websocket.messages;

public class RaiseMessageContent extends MessageContent {

    private int raiseValue;

    public RaiseMessageContent(int roomCode, int raiseValue) {
        super(roomCode);
        this.raiseValue = raiseValue;
    }

    public int getRaiseValue() {
        return raiseValue;
    }

    public void setRaiseValue(int raiseValue) {
        this.raiseValue = raiseValue;
    }
}
