package com.example.polypoker

import android.view.View
import com.example.polypoker.model.Card
import com.example.polypoker.model.CardNumber
import com.example.polypoker.model.CardSuit
import com.example.polypoker.model.Room
import com.example.polypoker.websocket.nv.SocketConnectionManager
import com.example.polypoker.websocket.stomp.WebSocketViewModel

class Utilities {
    companion object {
        lateinit var USER_LOGIN: String
        var USER_CASH: Int = 0
        lateinit var USER_NAME: String

        lateinit var currentRoom: Room;

        const val MAX_PLAYERS_COUNT: Int = 6

        const val HOST_ADDRESS = "ws://192.168.1.116:8080/room/websocket"

        var currentRoomCode: Int = -1

        var isPlaying = false

        lateinit var currentMainMenuView: View
        lateinit var currentRoomView: View

        lateinit var socketConnectionManager: SocketConnectionManager

        var webSocketViewModel: WebSocketViewModel? = null

        val cardsMap = mapOf<Card, Int>(
            Card() to R.drawable.ic_back_of_a_card,

            Card(CardSuit.HEARTS, CardNumber.TWO) to R.drawable.ic_2_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.THREE) to R.drawable.ic_3_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.FOUR) to R.drawable.ic_4_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.FIVE) to R.drawable.ic_5_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.SIX) to R.drawable.ic_6_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.SEVEN) to R.drawable.ic_7_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.EIGHT) to R.drawable.ic_8_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.NINE) to R.drawable.ic_9_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.TEN) to R.drawable.ic_10_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.JACK) to R.drawable.ic_jack_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.QUEEN) to R.drawable.ic_queen_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.KING) to R.drawable.ic_king_of_hearts,
            Card(CardSuit.HEARTS, CardNumber.ACE) to R.drawable.ic_ace_of_hearts,

            Card(CardSuit.DIAMONDS, CardNumber.TWO) to R.drawable.ic_2_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.THREE) to R.drawable.ic_3_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.FOUR) to R.drawable.ic_4_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.FIVE) to R.drawable.ic_5_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.SIX) to R.drawable.ic_6_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.SEVEN) to R.drawable.ic_7_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.EIGHT) to R.drawable.ic_8_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.NINE) to R.drawable.ic_9_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.TEN) to R.drawable.ic_10_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.JACK) to R.drawable.ic_jack_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.QUEEN) to R.drawable.ic_queen_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.KING) to R.drawable.ic_king_of_diamonds,
            Card(CardSuit.DIAMONDS, CardNumber.ACE) to R.drawable.ic_ace_of_diamonds,

            Card(CardSuit.CLUBS, CardNumber.TWO) to R.drawable.ic_2_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.THREE) to R.drawable.ic_3_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.FOUR) to R.drawable.ic_4_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.FIVE) to R.drawable.ic_5_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.SIX) to R.drawable.ic_6_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.SEVEN) to R.drawable.ic_7_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.EIGHT) to R.drawable.ic_8_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.NINE) to R.drawable.ic_9_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.TEN) to R.drawable.ic_10_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.JACK) to R.drawable.ic_jack_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.QUEEN) to R.drawable.ic_queen_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.KING) to R.drawable.ic_king_of_clubs,
            Card(CardSuit.CLUBS, CardNumber.ACE) to R.drawable.ic_ace_of_clubs,

            Card(CardSuit.SPADES, CardNumber.TWO) to R.drawable.ic_2_of_spades,
            Card(CardSuit.SPADES, CardNumber.THREE) to R.drawable.ic_3_of_spades,
            Card(CardSuit.SPADES, CardNumber.FOUR) to R.drawable.ic_4_of_spades,
            Card(CardSuit.SPADES, CardNumber.FIVE) to R.drawable.ic_5_of_spades,
            Card(CardSuit.SPADES, CardNumber.SIX) to R.drawable.ic_6_of_spades,
            Card(CardSuit.SPADES, CardNumber.SEVEN) to R.drawable.ic_7_of_spades,
            Card(CardSuit.SPADES, CardNumber.EIGHT) to R.drawable.ic_8_of_spades,
            Card(CardSuit.SPADES, CardNumber.NINE) to R.drawable.ic_9_of_spades,
            Card(CardSuit.SPADES, CardNumber.TEN) to R.drawable.ic_10_of_spades,
            Card(CardSuit.SPADES, CardNumber.JACK) to R.drawable.ic_jack_of_spades,
            Card(CardSuit.SPADES, CardNumber.QUEEN) to R.drawable.ic_queen_of_spades,
            Card(CardSuit.SPADES, CardNumber.KING) to R.drawable.ic_king_of_spades,
            Card(CardSuit.SPADES, CardNumber.ACE) to R.drawable.ic_ace_of_spades,

            )
    }
}