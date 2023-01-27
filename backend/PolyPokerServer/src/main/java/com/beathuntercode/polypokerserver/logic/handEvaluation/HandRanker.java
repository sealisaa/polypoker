package com.beathuntercode.polypokerserver.logic.handEvaluation;
import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.stream.Collectors;

import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.HandRank;
import com.beathuntercode.polypokerserver.logic.Rank;
import com.beathuntercode.polypokerserver.logic.Suit;

public class HandRanker {
    public static final int FULL_HAND = 5;
    public static final int FLUSH = FULL_HAND;
    public static final int STRAIGHT = FULL_HAND;
    public static final int FULL_HOUSE = FULL_HAND;
    public static final int QUADS = 4;
    public static final int SET = 3;
    public static final int PAIR = 2;
    public static final int HIGH_CARD = 1;

    public static final List<EnumSet<Rank>> STRAIGHTS = setPossibleStraights();
    private static final HandRanker instance = new HandRanker();

    public static HandRanker getInstance() {
        return instance;
    }

    private List<Card> handCards = new ArrayList<>();
    private PokerHand pokerHand;

    private HandRanker() {}

    private static List<EnumSet<Rank>> setPossibleStraights() {
        List<EnumSet<Rank>> straights =  EnumSet.range(Rank.TWO, Rank.TEN).stream()
                .map(rank -> EnumSet.range(rank, Rank.values()[rank.ordinal() + STRAIGHT - 1]))
                .collect(toList());
        Collections.reverse(straights);
        straights.add(EnumSet.of(Rank.ACE, Rank.TWO, Rank.THREE, Rank.FOUR, Rank.FIVE));
        return Collections.unmodifiableList(straights);
    }

    public PokerHand getRank(List<Card> allCards) {
        if(allCards.size() != 7) throw new IllegalArgumentException("You have to pass 7 cards");

        if(     isStraightFlush(allCards) ||
                isFourOfAKind(allCards) ||
                isFullHouse(allCards) ||
                isFlush(allCards, true) ||
                isStraight(allCards) ||
                isSet(allCards) ||
                isTwoPair(allCards) ||
                isPair(allCards)) {
        }
        else {  // if isHighCard(allCards)
            Collections.sort(allCards);
            pokerHand = new PokerHand(HandRank.HIGH_CARD, new ArrayList<>(Arrays.asList(allCards.get(0))));
        }
        return pokerHand;
    }


    private boolean isStraightFlush(List<Card> allCards) {
        if(isFlush(allCards, false) && isStraight(handCards)) {
            pokerHand = new PokerHand(HandRank.STRAIGHT_FLUSH, handCards);
            return true;
        }
        return false;
    }

    private boolean isFlush(List<Card> allCards, boolean finalResult) {
        Map<Suit, Long> suitsMap = getSuitMap(allCards);
        Suit popularSuit = getMostPopularSuit(suitsMap);

        if(suitsMap.get(popularSuit) >= FLUSH) {

            if(finalResult) {
                handCards = allCards.stream()
                        .filter(c -> c.getSuit() == popularSuit)
                        .sorted()
                        .limit(FULL_HAND)
                        .collect(Collectors.toList());

                pokerHand = new PokerHand(HandRank.FLUSH, handCards);
            } else {
                handCards = allCards.stream()
                        .filter(c -> c.getSuit() == popularSuit)
                        .collect(Collectors.toList());
            }
            return true;
        }
        return false;
    }

    private boolean isStraight(List<Card> allCards) {
        EnumSet<Rank> ranks = allCards.stream()
                .map(Card::getRank)
                .collect(toCollection(() -> EnumSet.noneOf(Rank.class)));

        for(Set<Rank> straight : STRAIGHTS) {
            if(ranks.containsAll(straight))  {
                handCards = allCards.stream()
                        .filter(c -> straight.contains(c.getRank()))
                        .collect(toList());

                pokerHand = new PokerHand(HandRank.STRAIGHT, handCards);
                return true;
            }
        }
        return false;
    }

    private boolean isFourOfAKind(List<Card> allCards) {
        handCards = getHighestCards(allCards, QUADS);

        if(handCards.size() == QUADS) {
            handCards.addAll(getMultipleHighestCards(allCards, FULL_HAND - QUADS));
            pokerHand = new PokerHand(HandRank.FOUR_OF_A_KIND, handCards);
            return true;
        }
        return false;
    }

    private boolean isFullHouse(List<Card> allCards) {
        handCards = getHighestCards(allCards, SET);
        handCards.addAll(getHighestCards(allCards, PAIR));

        if(handCards.size() == FULL_HOUSE) {
            pokerHand = new PokerHand(HandRank.FULL_HOUSE, handCards);
            return true;
        }
        return false;
    }

    private boolean isSet(List<Card> allCards) {
        handCards = getHighestCards(allCards, SET);

        if(handCards.size() == SET) {
            handCards.addAll(getMultipleHighestCards(allCards, FULL_HAND - SET));
            pokerHand = new PokerHand(HandRank.THREE_OF_A_KIND, handCards);
            return true;
        }
        return false;
    }

    private boolean isTwoPair(List<Card> allCards) {
        handCards= getHighestCards(allCards, PAIR);
        allCards.removeAll(handCards);
        handCards.addAll(getHighestCards(allCards, PAIR));

        if(handCards.size() == PAIR + PAIR) {
            handCards.addAll(getMultipleHighestCards(allCards, FULL_HAND - PAIR - PAIR));
            pokerHand = new PokerHand(HandRank.TWO_PAIR, handCards);
            return true;
        }
        return false;
    }

    private boolean isPair(List<Card> allCards) {
        handCards = getHighestCards(allCards, PAIR);

        if(handCards.size() == PAIR) {
            handCards.addAll(getMultipleHighestCards(allCards, FULL_HAND - PAIR));
            pokerHand = new PokerHand(HandRank.PAIR, handCards);
            return true;
        }
        return false;
    }

    private boolean isHighCard(List<Card> allCards) {
        handCards = getHighestCards(allCards, FULL_HAND);
        pokerHand = new PokerHand(HandRank.HIGH_CARD, handCards);
        return true;
    }

    private List<Card> getHighestCards(List<Card> allCards, int count) {
        EnumMap<Rank, Long> ranks = getRankMap(allCards);

        try {
            Rank cardsRank = ranks.entrySet().stream()
                    .filter(entry -> entry.getValue() == count)
                    .reduce((previous, current) -> current)
                    .get().getKey(); //throws exception if there is not same rank cards with specified count

            return allCards.stream()
                    .filter( c -> c.getRank() == cardsRank)
                    .collect(toList());
        }
        catch(NoSuchElementException e) {
            return new ArrayList<>();
        }
    }

    private List<Card> getMultipleHighestCards(List<Card> allCards, int count) {
        List<Card> highestCards = new ArrayList<>(count);
        for(int index = 0; index < count; index++) {
            List<Card> cards = getHighestCards(allCards, HIGH_CARD);
            allCards.removeAll(cards);
            highestCards.addAll(cards);
        }
        return highestCards;
    }

    private Suit getMostPopularSuit(Map<Suit, Long> suits) {
        return suits.entrySet().stream()
                .max((lhs, rhs) -> lhs.getValue() > rhs.getValue() ? 1 : -1)
                .get().getKey();
    }

    private EnumMap<Suit, Long> getSuitMap(List<Card> allCards) {
        return allCards.stream()
                .collect(groupingBy(
                        Card::getSuit,
                        () -> new EnumMap<>(Suit.class),
                        counting()
                ));
    }

    private EnumMap<Rank, Long> getRankMap(List<Card> allCards) {
        return allCards.stream()
                .collect(groupingBy(
                        Card::getRank,
                        () -> new EnumMap<>(Rank.class),
                        counting()
                ));
    }
}
