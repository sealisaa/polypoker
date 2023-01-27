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
        bank = 0;
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
                startTern();
            }
            case TERN -> {
                gameState = GameState.RIVER;
                startRiver();
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

    private void startTern() {
        faceUp.add(dealRandomCard());
    }

    private void startRiver() {
        faceUp.add(dealRandomCard());
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
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

    public Integer getTimesPlayerAskedForFaceUpsPlayer(String player) {
        return timesPlayerAskedForFaceUps.get(player);
    }

    public Map<String, Integer> getTimesPlayerAskedForFaceUps() {
        return timesPlayerAskedForFaceUps;
    }

    public void incrementTimesPlayerAskedForFaceUps(String player) {
        timesPlayerAskedForFaceUps.put(player, timesPlayerAskedForFaceUps.get(player) + 1);
    }

    public void defineCardCombination() {
        for (Player player : playersMap.values().stream().toList()) {
            if (player.getCard1().getRank() == player.getCard2().getRank()) {

            }
            else {
                player.setCardCombination(HAND_RANK.HIGH_CARD);
            }
        }
    }

    private boolean isOnePair(Player player) {
        if (    player.getCard1().getRank() == player.getCard2().getRank() ||
                player.getCard1().getRank() == faceUp.get(0).getRank() ||
                player.getCard1().getRank() == faceUp.get(1).getRank() ||
                player.getCard1().getRank() == faceUp.get(2).getRank() ||
                player.getCard1().getRank() == faceUp.get(3).getRank() ||
                player.getCard1().getRank() == faceUp.get(4).getRank()
        ) {
            return true;
        }
        else if (
                player.getCard2().getRank() == player.getCard1().getRank() ||
                player.getCard2().getRank() == faceUp.get(0).getRank() ||
                player.getCard2().getRank() == faceUp.get(1).getRank() ||
                player.getCard2().getRank() == faceUp.get(2).getRank() ||
                player.getCard2().getRank() == faceUp.get(3).getRank() ||
                player.getCard2().getRank() == faceUp.get(4).getRank()
        ) {
           return true;
        }
        else if (
                faceUp.get(0).getRank() == player.getCard1().getRank() ||
                faceUp.get(0).getRank() == player.getCard2().getRank() ||
                faceUp.get(0).getRank() == faceUp.get(1).getRank() ||
                faceUp.get(0).getRank() == faceUp.get(2).getRank() ||
                faceUp.get(0).getRank() == faceUp.get(3).getRank() ||
                faceUp.get(0).getRank() == faceUp.get(4).getRank()
        )
        {
            return true;
        }
        else return false;
    }
}
