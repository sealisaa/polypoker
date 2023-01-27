package com.beathuntercode.polypokerserver.logic.handEvaluation;

import com.beathuntercode.polypokerserver.logic.Card;
import java.util.Collections;
import java.util.List;

import com.beathuntercode.polypokerserver.logic.HandRank;

public class PokerHand implements Comparable {

    private final HandRank handRank;
    private final List<Card> cards;

    public PokerHand(HandRank handRank, List<Card> cards) {
//        if(cards.size() != HandRanker.FULL_HAND) throw new IllegalArgumentException("You have to pass five cards");
        this.handRank = handRank;
        this.cards = cards;
        Collections.sort(this.cards); //sorts for compareTo method
    }

    @Override
    public String toString() {
        return "HandValue{" +
                "handRank=" + handRank +
                ", allCards=" + cards +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(this == o) return 0;
        if(o == null || getClass() != o.getClass()) return 0;

        PokerHand pokerHand = (PokerHand) o;

        if(handRank.getValue() > pokerHand.handRank.getValue()) return 1;
        if(handRank.getValue() < pokerHand.handRank.getValue()) return -1;

        for(int index = 0; index < cards.size(); index++) {
            if(cards.get(index).getRank().getValue() > pokerHand.cards.get(index).getRank().getValue()) return 1;
            if(cards.get(index).getRank().getValue() < pokerHand.cards.get(index).getRank().getValue()) return -1;
        }
        return 0;
    }
}
