package com.beathuntercode.polypokerserver.logic;

import com.beathuntercode.polypokerserver.logic.handEvaluation.PokerHand;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class Player {

    private String login;
    private String name;
    private int currentStake;
    private int cash;
    private Card card1;
    private Card card2;
    private PokerHand hand;
    private boolean isReady;
    private boolean isCheck;
    private boolean isFold;
    private boolean isBet;
    private boolean isSmallBlind;
    private boolean isBigBlind;
    private int playerAvatarNumber;

    private boolean mustMakeBet;
    private int playerNumberInRoom;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentStake() {
        return currentStake;
    }

    public void setCurrentStake(int currentStake) {
        this.currentStake = currentStake;
    }

    public void increaseStake(int moneyValue) {
        currentStake += moneyValue;
    }

    public void decreaseStake(int moneyValue) {
        currentStake -= moneyValue;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public PokerHand getHand() {
        return hand;
    }

    public void setHand(PokerHand hand) {
        this.hand = hand;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isFold() {
        return isFold;
    }

    public void setFold(boolean fold) {
        isFold = fold;
    }

    public boolean isBet() {
        return isBet;
    }

    public void setBet(boolean bet) {
        isBet = bet;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isSmallBlind() {
        return isSmallBlind;
    }

    public void setSmallBlind(boolean smallBlind) {
        isSmallBlind = smallBlind;
    }

    public boolean isBigBlind() {
        return isBigBlind;
    }

    public void setBigBlind(boolean bigBlind) {
        isBigBlind = bigBlind;
    }

    public boolean isMustMakeBet() {
        return mustMakeBet;
    }

    public void setMustMakeBet(boolean mustMakeBet) {
        this.mustMakeBet = mustMakeBet;
    }

    public int getPlayerAvatarNumber() {
        return playerAvatarNumber;
    }

    public int getPlayerNumberInRoom() {
        return playerNumberInRoom;
    }

    public void setPlayerNumberInRoom(int playerNumberInRoom) {
        this.playerNumberInRoom = playerNumberInRoom;
    }

    public void setPlayerAvatarNumber(int playersAvatarNumber) {
        this.playerAvatarNumber = playersAvatarNumber;
    }

    public Player(String login, String name, int currentStake, int cash, int playersAvatarNumber, int playerNumberInRoom) {
        this.login = login;
        this.name = name;
        this.currentStake = currentStake;
        this.cash = cash;
        this.playerAvatarNumber = playersAvatarNumber;
        this.playerNumberInRoom = playerNumberInRoom;
        isReady = false;
        isCheck = false;
        isFold = false;
        isSmallBlind = false;
        isBigBlind = false;
        mustMakeBet = true;
    }

    @Override
    public String toString() {
        return "Player{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", currentStake=" + currentStake +
                ", cash=" + cash +
                ", card1=" + card1 +
                ", card2=" + card2 +
                ", hand=" + hand +
                ", isReady=" + isReady +
                ", isCheck=" + isCheck +
                ", isFold=" + isFold +
                ", isSmallBlind=" + isSmallBlind +
                ", isBigBlind=" + isBigBlind +
                ", playerAvatarNumber=" + playerAvatarNumber +
                ", mustMakeBet=" + mustMakeBet +
                '}';
    }
}
