package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameManager {


    private Map<String, Player> playersMap;
    private int bank;

    private ArrayList<Card> deck;
    private GameState gameState;

    public GameManager(Map<String, Player> playersMap) {
        this.playersMap = playersMap;
        deck = Utilities.cardList;
    }

    public void changeGameStateToNext() {
        switch (gameState) {
            case BLINDS -> {
                gameState = GameState.PREFLOP;
            }
            case PREFLOP -> {
                gameState = GameState.FLOP;
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

    private void startBlinds() {
        Player smallBlindPlayer = playersMap.entrySet().stream().filter(entry -> entry.getValue().isSmallBlind()).findFirst().get().getValue();
        Player bigBlindPlayer = playersMap.entrySet().stream().filter(entry -> entry.getValue().isBigBlind()).findFirst().get().getValue();

        //TODO: Реализовать запросы сервера к клиенту на блайнды
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
