package com.example.polypoker.model;

public class Room {

    private int roomCode;
    private int minBlind;
    private int minRaise;

    public int getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }

    public int getMinBlind() {
        return minBlind;
    }

    public void setMinBlind(int minBlind) {
        this.minBlind = minBlind;
    }

    public int getMinRaise() {
        return minRaise;
    }

    public void setMinRaise(int minRaise) {
        this.minRaise = minRaise;
    }


    public Room(int roomCode, int minBlind, int minRaise) {
        this.roomCode = roomCode;
        this.minBlind = minBlind;
        this.minRaise = minRaise;
    }

}
