package com.beathuntercode.polypokerserver.logic;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class Player {

    private String login;
    private String name;
    private int currentStake;
    private int cash;
    private Card card1;
    private Card card2;
    private boolean isReady;
    private boolean isSmallBlind;
    private boolean isBigBlind;


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

    public Player(String login, String name, int currentStake, int cash) {
        this.login = login;
        this.name = name;
        this.currentStake = currentStake;
        this.cash = cash;
        isReady = false;
        isSmallBlind = false;
        isBigBlind = false;
    }

}
