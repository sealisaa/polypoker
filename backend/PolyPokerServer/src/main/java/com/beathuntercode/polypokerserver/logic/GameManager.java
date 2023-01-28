package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private Player winnerPlayer;

    public GameManager(Map<String, Player> playersMap) {
        this.playersMap = playersMap;
        deck = Utilities.cardList;
        faceUp = new ArrayList<Card>();
        timesPlayerAskedForFaceUps = new HashMap<>();
        bank = 0;
        playersMap.entrySet().forEach(player -> timesPlayerAskedForFaceUps.put(player.getKey(), 0));
    }

    public void changeGameStateToNext() {
        resetChecks();
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
                startShowdown();
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

    private void resetChecks() {
        for (Player player : playersMap.values()) {
            player.setCheck(false);
        }
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
    private void startShowdown() {
        definePlayersHands();
        winnerPlayer = defineWinner();
        System.out.println();
        System.out.println("------------- GAME RESULTS:\n" +
                "\tWinner - " + winnerPlayer.getLogin() + "; " + winnerPlayer.getHand() + "\n" +
                "\tPlayers Hands:");
        for (Map.Entry<String, Player> entry : playersMap.entrySet()) {
            System.out.println("\t\t" + entry.getKey() + ": " + entry.getValue().getHand().toString());
        }
        System.out.println();
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

        System.out.println("------------- Players Hands:\n");
        for (Map.Entry<String, Player> entry : playersMap.entrySet()) {
            System.out.println("\t" + entry.getKey() + ": " + entry.getValue().getHand().toString());
        }
    }

    public Player defineWinner() {
        Map<String, PokerHand> playersHandsMap = new LinkedHashMap<>();
        for (Player player : playersMap.values()) {
            if (!player.isFold()) {
                playersHandsMap.put(player.getLogin(), player.getHand());
            }
        }

        List<Map.Entry<String, PokerHand>> playersHandsList = new ArrayList<>(playersHandsMap.entrySet());
        playersHandsList.sort(Map.Entry.comparingByValue());
        Collections.reverse(playersHandsList);

        Player winner = playersMap.get(playersHandsList.stream().toList().get(0).getKey());
        Player winnerForLambda = winner;
        long equalsHandsCount =
                playersHandsList.stream().filter(entry ->
                        entry.getValue().getHandRank().equals(winnerForLambda.getHand().getHandRank())).count();
        if (equalsHandsCount > 1) {
            Map<String, PokerHand> highestCardsHands = new TreeMap<>();
            for (Map.Entry<String, PokerHand> entry : playersHandsMap.entrySet()) {
                highestCardsHands.put(
                        entry.getKey(),
                        new PokerHand(
                                HandRank.HIGH_CARD,
                                HandRanker.getInstance().getHighestCards(entry.getValue().getCards(), 1))
                );
            }
            for (Map.Entry<String, PokerHand> entry : playersHandsMap.entrySet()) {
                if (entry.getKey().equals(highestCardsHands.entrySet().stream().toList().get(0).getKey())) {
                    winner = playersMap.get(entry.getKey());
                    break;
                }
            }
        }
        return winner;

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

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(Player winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }
}
