package com.example.polypoker.model;

import android.widget.ImageView;
import android.widget.TextView;

public class Player {

    private String login;
    private String name;
    private int cash;
    private int currentStake;
    private Card card1;
    private Card card2;

    private ImageView card1ImageView;
    private ImageView card2ImageView;
    private TextView cashTextView;


    public Player(String login, String name, int cash, int currentStake, Card card1, Card card2, ImageView card1ImageView, ImageView card2ImageView, TextView cashTextView) {
        this.login = login;
        this.name = name;
        this.cash = cash;
        this.currentStake = currentStake;
        this.card1 = card1;
        this.card2 = card2;
        this.card1ImageView = card1ImageView;
        this.card2ImageView = card2ImageView;
        this.cashTextView = cashTextView;
    }

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

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCurrentStake() {
        return currentStake;
    }

    public void setCurrentStake(int currentStake) {
        this.currentStake = currentStake;
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

    public ImageView getCard1ImageView() {
        return card1ImageView;
    }

    public void setCard1ImageView(ImageView card1ImageView) {
        this.card1ImageView = card1ImageView;
    }

    public ImageView getCard2ImageView() {
        return card2ImageView;
    }

    public void setCard2ImageView(ImageView card2ImageView) {
        this.card2ImageView = card2ImageView;
    }

    public TextView getCashTextView() {
        return cashTextView;
    }

    public void setCashTextView(TextView cashTextView) {
        this.cashTextView = cashTextView;
    }
}
