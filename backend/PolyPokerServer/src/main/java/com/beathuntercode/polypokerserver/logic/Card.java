package com.beathuntercode.polypokerserver.logic;

import java.util.Objects;

public class Card implements Comparable {
    private SUIT suit;
    private RANK rank;

    public SUIT getSuit() {
        return suit;
    }

    public void setSuit(SUIT SUIT) {
        this.suit = SUIT;
    }

    public RANK getRank() {
        return rank;
    }

    public void setRank(RANK RANK) {
        this.rank = RANK;
    }

    public Card(SUIT suit, RANK rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardSuit=" + suit +
                ", cardNumber=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit == card.suit && rank == card.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public int compareTo(Object o) {
        if(this == o) return 0;
        if(o == null || getClass() != o.getClass()) return 0;

        Card card = (Card) o;

        if(rank.getValue() > card.rank.getValue()) return -1;
        if(rank.getValue() < card.rank.getValue()) return 1;
        return 0;
    }
}
