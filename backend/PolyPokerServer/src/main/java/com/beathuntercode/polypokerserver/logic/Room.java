package com.beathuntercode.polypokerserver.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Map<String, Player> getPlayersMap() {
        return playersMap;
    }

    public void setPlayersMap(Map<String, Player> playersMap) {
        this.playersMap = playersMap;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private Map<String, Player> playersMap;

    private GameManager gameManager;

    public Room(int roomCode, int minBlind, int minRaise) {
        this.roomCode = roomCode;
        this.minBlind = minBlind;
        this.minRaise = minRaise;

        playersMap = new HashMap<>();
        gameManager = new GameManager(playersMap);
    }

}
