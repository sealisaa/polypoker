package com.example.polypoker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {
    public static GameState currentGameState;

    public static Map<String, Player> playersMap = new HashMap<>();

    public static Card TABLE_CARD1;
    public static Card TABLE_CARD2;
    public static Card TABLE_CARD3;
    public static Card TABLE_CARD4;
    public static Card TABLE_CARD5;

    public static void nextGameState() {
        switch (currentGameState) {
            case BLINDS:
                currentGameState = GameState.PREFLOP;
                break;
            case PREFLOP:
                currentGameState = GameState.FLOP;
                break;
            case FLOP:
                currentGameState = GameState.TERN;
                break;
            case TERN:
                currentGameState = GameState.RIVER;
                break;
            case RIVER:
                currentGameState = GameState.SHOWDOWN;
                break;
            case SHOWDOWN:
                break;
        }
    }
}
