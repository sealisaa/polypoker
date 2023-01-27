package com.beathuntercode.polypokerserver.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {

    private int roomCode;
    private int minBlind;
    private int minRaise;
    private Map<String, Player> playersMap;
    private GameManager gameManager;

    private boolean isSmallBlindSet;
    private boolean isBigBlindSet;

    private Player smallBlindPlayer;
    private Player bigBlindPlayer;

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

    public boolean isSmallBlindSet() {
        return isSmallBlindSet;
    }

    public void setSmallBlindSet(boolean smallBlindSet) {
        isSmallBlindSet = smallBlindSet;
    }

    public boolean isBigBlindSet() {
        return isBigBlindSet;
    }

    public void setBigBlindSet(boolean bigBlindSet) {
        isBigBlindSet = bigBlindSet;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public Player getSmallBlindPlayer() {
        return smallBlindPlayer;
    }

    public void setSmallBlindPlayer(Player smallBlindPlayer) {
        this.smallBlindPlayer = smallBlindPlayer;
    }

    public Player getBigBlindPlayer() {
        return bigBlindPlayer;
    }

    public void setBigBlindPlayer(Player bigBlindPlayer) {
        this.bigBlindPlayer = bigBlindPlayer;
    }

    public void removePlayerFromRoom(String playerLogin) {
        playersMap.remove(playerLogin);
        gameManager.getTimesPlayerAskedForFaceUps().remove(playerLogin);
        if (playersMap.size() == 0) {
            System.out.println("--------------- RESTART ROOM ---------------");
            initRoom();
        }
    }

    public void initRoom() {
        playersMap = new HashMap<>();
        gameManager = new GameManager(playersMap);
        isSmallBlindSet = false;
        isBigBlindSet = false;
        smallBlindPlayer = null;
        bigBlindPlayer = null;
    }

    public Room(int roomCode, int minBlind, int minRaise) {
        this.roomCode = roomCode;
        this.minBlind = minBlind;
        this.minRaise = minRaise;

        initRoom();
    }

}
