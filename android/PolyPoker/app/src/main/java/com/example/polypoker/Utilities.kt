package com.example.polypoker

import com.example.polypoker.model.Card
import com.example.polypoker.model.CardNumber
import com.example.polypoker.model.CardSuit

class Utilities {
    companion object {
        lateinit var USER_LOGIN: String

        const val MAX_PLAYERS_COUNT: Int = 6

        val cardsMap = mapOf<Card, String>(
            Card(CardSuit.HEARTS, CardNumber.TWO) to "ic_2_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.THREE) to "ic_3_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.FOUR) to "ic_4_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.FIVE) to "ic_5_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.SIX) to "ic_6_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.SEVEN) to "ic_7_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.EIGHT) to "ic_8_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.NINE) to "ic_9_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.TEN) to "ic_10_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.JACK) to "ic_jack_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.QUEEN) to "ic_queen_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.KING) to "ic_king_of_hearts",
            Card(CardSuit.HEARTS, CardNumber.ACE) to "ic_ace_of_hearts",

            Card(CardSuit.DIAMONDS, CardNumber.TWO) to "ic_2_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.THREE) to "ic_3_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.FOUR) to "ic_4_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.FIVE) to "ic_5_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.SIX) to "ic_6_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.SEVEN) to "ic_7_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.EIGHT) to "ic_8_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.NINE) to "ic_9_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.TEN) to "ic_10_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.JACK) to "ic_jack_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.QUEEN) to "ic_queen_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.KING) to "ic_king_of_diamonds",
            Card(CardSuit.DIAMONDS, CardNumber.ACE) to "ic_ace_of_diamonds",

            Card(CardSuit.CLUBS, CardNumber.TWO) to "ic_2_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.THREE) to "ic_3_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.FOUR) to "ic_4_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.FIVE) to "ic_5_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.SIX) to "ic_6_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.SEVEN) to "ic_7_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.EIGHT) to "ic_8_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.NINE) to "ic_9_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.TEN) to "ic_10_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.JACK) to "ic_jack_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.QUEEN) to "ic_queen_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.KING) to "ic_king_of_clubs",
            Card(CardSuit.CLUBS, CardNumber.ACE) to "ic_ace_of_clubs",

            Card(CardSuit.SPADES, CardNumber.TWO) to "ic_2_of_spades",
            Card(CardSuit.SPADES, CardNumber.THREE) to "ic_3_of_spades",
            Card(CardSuit.SPADES, CardNumber.FOUR) to "ic_4_of_spades",
            Card(CardSuit.SPADES, CardNumber.FIVE) to "ic_5_of_spades",
            Card(CardSuit.SPADES, CardNumber.SIX) to "ic_6_of_spades",
            Card(CardSuit.SPADES, CardNumber.SEVEN) to "ic_7_of_spades",
            Card(CardSuit.SPADES, CardNumber.EIGHT) to "ic_8_of_spades",
            Card(CardSuit.SPADES, CardNumber.NINE) to "ic_9_of_spades",
            Card(CardSuit.SPADES, CardNumber.TEN) to "ic_10_of_spades",
            Card(CardSuit.SPADES, CardNumber.JACK) to "ic_jack_of_spades",
            Card(CardSuit.SPADES, CardNumber.QUEEN) to "ic_queen_of_spades",
            Card(CardSuit.SPADES, CardNumber.KING) to "ic_king_of_spades",
            Card(CardSuit.SPADES, CardNumber.ACE) to "ic_ace_of_spades",
        )
    }
}