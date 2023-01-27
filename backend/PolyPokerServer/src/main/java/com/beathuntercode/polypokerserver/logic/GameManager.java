package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.beathuntercode.polypokerserver.logic.handEvaluation.HandRanker;
import com.beathuntercode.polypokerserver.logic.handEvaluation.PokerHand;

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

    public void definePlayersHands() {
        for (Player player : playersMap.values()) {
            List<Card> playersCards = new ArrayList<>();
            playersCards.add(player.getCard1());
            playersCards.add(player.getCard2());
            playersCards.add(getFaceUp().get(0));
            playersCards.add(getFaceUp().get(1));
            playersCards.add(getFaceUp().get(2));
            playersCards.add(getFaceUp().get(3));
            playersCards.add(getFaceUp().get(4));
            player.setHand(HandRanker.getInstance().getRank(playersCards));
        }
    }

    public Player defineWinner() {
        Map<String, PokerHand> playersHands = new TreeMap<>();
        for (Player player : playersMap.values()) {
            playersHands.put(player.getLogin(), player.getHand());
        }
        return playersMap.get(playersHands.entrySet().stream().toList().get(0).getKey());
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
}
