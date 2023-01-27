package com.beathuntercode.polypokerserver.logic;

import java.util.Objects;

public class Card implements Comparable {
    private Suit suit;
    private Rank rank;

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit SUIT) {
        this.suit = SUIT;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank RANK) {
        this.rank = RANK;
    }

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Card{" +
                "suit=" + suit +
                ", rank=" + rank +
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
