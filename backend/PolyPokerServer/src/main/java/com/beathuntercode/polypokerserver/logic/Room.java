package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    public List<Player> getPlayersList() {
        return playersList;
    }

    public void setPlayersList(List<Player> playersList) {
        this.playersList = playersList;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    private List<Player> playersList;

    private GameManager gameManager;

    public Room(int roomCode, int minBlind, int minRaise) {
        this.roomCode = roomCode;
        this.minBlind = minBlind;
        this.minRaise = minRaise;

        playersList = new ArrayList<>();
        gameManager = new GameManager(playersList);
    }

}
