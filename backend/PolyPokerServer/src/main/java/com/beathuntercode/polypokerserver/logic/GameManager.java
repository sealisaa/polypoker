package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameManager {


    private Map<String, Player> playersMap;
    private int bank;

    private ArrayList<Card> deck;
    private ArrayList<Card> faceUp;
    private GameState gameState;
    private Map<String, Integer> timesPlayerAskedForFaceUps;

    public GameManager(Map<String, Player> playersMap) {
        this.playersMap = playersMap;
        deck = Utilities.cardList;
        faceUp = new ArrayList<Card>();
        timesPlayerAskedForFaceUps = new HashMap<>();
        playersMap.entrySet().forEach(player -> timesPlayerAskedForFaceUps.put(player.getKey(), 0));
    }

    public void changeGameStateToNext() {
        switch (gameState) {
            case BLINDS -> {
                gameState = GameState.PREFLOP;
            }
            case PREFLOP -> {
                gameState = GameState.FLOP;
                startFlop();
            }
            case FLOP -> {
                gameState = GameState.TERN;
            }
            case TERN -> {
                gameState = GameState.RIVER;
            }
            case RIVER -> {
                gameState = GameState.SHOWDOWN;
            }
            case SHOWDOWN -> {
                break;
            }
            default -> {
                gameState = GameState.BLINDS;
            }
        }
    }

    public Card dealRandomCard() {
        Card card = deck.get(Utilities.getRndIntInRange(0, deck.size() - 1));
        deck.remove(card);
        return card;
    }

    private void startFlop() {
        faceUp.add(dealRandomCard());
        faceUp.add(dealRandomCard());
        faceUp.add(dealRandomCard());
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void increaseBank(int moneyValue) {
        bank += moneyValue;
    }

    public void decreaseBank(int moneyValue) {
        bank -= moneyValue;
    }

    public ArrayList<Card> getFaceUp() {
        return faceUp;
    }

    public void setFaceUp(ArrayList<Card> faceUp) {
        this.faceUp = faceUp;
    }

    public Integer getTimesPlayerAskedForFaceUps(String player) {
        return timesPlayerAskedForFaceUps.get(player);
    }

    public void incrementTimesPlayerAskedForFaceUps(String player) {
        timesPlayerAskedForFaceUps.put(player, timesPlayerAskedForFaceUps.get(player) + 1);
    }
}
