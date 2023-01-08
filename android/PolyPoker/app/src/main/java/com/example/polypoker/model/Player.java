package com.example.polypoker.model;

public class Player {
    private String name;
    private int cash;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    private Card card1;
    private Card card2;

    public Player(String name, int cash, Card card1, Card card2) {
        this.name = name;
        this.cash = cash;
        this.card1 = card1;
        this.card2 = card2;
    }
}
