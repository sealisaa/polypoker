package com.beathuntercode.polypokerserver.websocket;

public enum MessageType {
    PLAYER_ROOM_CONNECT(4);

    private int value;

    MessageType(int value) {
        this.value = value;
    }
}
