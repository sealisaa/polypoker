package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {


    private List<Player> playersList;
    private int bank;

    private List<Card> deck;
    private GameState gameState;

    public GameManager(List<Player> playersList) {
        this.playersList = playersList;
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
        for (Player player : playersList) {
            if (!player.isReady()) {
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
        for (Player player: playersList) {
            player.setCard1(dealRandomCard());
            player.setCard2(dealRandomCard());
        }
        playersList.get(0).setSmallBlind(true);
        playersList.get(1).setBigBlind(true);
    }

    private void startBlinds() {
        Player smallBlindPlayer = playersList.stream().filter(Player::isSmallBlind).findFirst().get();
        Player bigBlindPlayer = playersList.stream().filter(Player::isBigBlind).findFirst().get();

        //TODO: Реализовать запросы сервера к клиенту на блайнды
    }

}
