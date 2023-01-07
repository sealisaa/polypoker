package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GameManager {


    private Map<String, Player> playersMap;
    private int bank;

    private List<Card> deck;
    private GameState gameState;

    public GameManager(Map<String, Player> playersMap) {
        this.playersMap = playersMap;
    }

    public void checkGameState() {
        switch (gameState) {
            case BLINDS -> {
                break;
            }
            case PREFLOP -> {
                break;
            }
            case FLOP -> {
                break;
            }
            case TERN -> {
                break;
            }
            case RIVER -> {
                break;
            }
            case SHOWDOWN -> {
                break;
            }
        }
    }

    private boolean isAllPlayersReady() {
        boolean isAllPlayersReady = false;
        for (Map.Entry<String, Player> entry : playersMap.entrySet()) {
            if (!entry.getValue().isReady()) {
                isAllPlayersReady = false;
            }
            else {
                isAllPlayersReady = true;
            }
        }
        return isAllPlayersReady;
    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (CardSuit cardSuit : CardSuit.values()) {
            for (CardNumber cardNumber : CardNumber.values()) {
                Card card = new Card(cardSuit, cardNumber);
                deck.add(card);
            }
        }
        return deck;
    }

    private void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    private Card dealRandomCard() {
        return deck.get(Utilities.getRndIntInRange(0, deck.size() - 1));
    }

    private void startGame() {
        for (Map.Entry<String, Player> entry : playersMap.entrySet()) {
            entry.getValue().setCard1(dealRandomCard());
            entry.getValue().setCard2(dealRandomCard());
        }
        playersMap.get(0).setSmallBlind(true);
        playersMap.get(1).setBigBlind(true);
    }

    private void startBlinds() {
        Player smallBlindPlayer = playersMap.entrySet().stream().filter(entry -> entry.getValue().isSmallBlind()).findFirst().get().getValue();
        Player bigBlindPlayer = playersMap.entrySet().stream().filter(entry -> entry.getValue().isBigBlind()).findFirst().get().getValue();

        //TODO: Реализовать запросы сервера к клиенту на блайнды
    }

}
