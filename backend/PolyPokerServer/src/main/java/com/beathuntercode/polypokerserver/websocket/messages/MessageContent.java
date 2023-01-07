package com.beathuntercode.polypokerserver.websocket.messages;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageContent {

    private int roomCode;

    public MessageContent(int roomCode) {
        this.roomCode = roomCode;
    }

    public int getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }
}
