package com.example.polypoker.websocket.stomp

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.polypoker.R
import com.example.polypoker.Utilities
import com.example.polypoker.model.Card
import com.example.polypoker.model.GameManager
import com.example.polypoker.model.Player
import com.example.polypoker.websocket.stomp.MessageType.*
import java.time.LocalDateTime

class MessageHandler(
    private var roomView: View,
    private var roomViewModel: WebSocketViewModel
) {

    public fun handleMessage(message: SocketMessage) {
        when (message.messageType) {
            PLAYER_READY_SET -> TODO()
            PLAYER_ROOM_EXIT -> TODO()
            ROUND_BEGIN -> {
                roomView.findViewById<Button>(R.id.readyButton).visibility = View.INVISIBLE
                roomView.findViewById<Button>(R.id.notReadyButton).visibility = View.INVISIBLE
                roomView.findViewById<TextView>(R.id.bankMoneyCount).text = "0"

                for (player in GameManager.playersMap.values) {
                    if (player.card1 == null) {
                        drawCardRequest()
                    }
                    if (player.card2 == null) {
                        drawCardRequest()
                    }
                }

                if (GameManager.TABLE_CARD1 == null) {
                    drawCardRequest()
                }
                if (GameManager.TABLE_CARD2 == null) {
                    drawCardRequest()
                }
                if (GameManager.TABLE_CARD3 == null) {
                    drawCardRequest()
                }
            }
            DRAW_CARD -> {
                if (message.content.getUserLogin() != null) {
                    val player = GameManager.playersMap[message.content.getUserLogin()]
                    if (player?.card1 == null) {
                        player?.card1 = Card(
                            message.content.getCardSuit(),
                            message.content.getCardNumber()
                        )
                    } else if (player.card2 == null) {
                        player.card2 = Card(
                            message.content.getCardSuit(),
                            message.content.getCardNumber()
                        )
                    }
                }
                else {
                    if (GameManager.TABLE_CARD1 == null) {
                        GameManager.TABLE_CARD1 = Card(
                            message.content.getCardSuit(),
                            message.content.getCardNumber()
                        )
                    }
                    if (GameManager.TABLE_CARD2 == null) {
                        GameManager.TABLE_CARD2 = Card(
                            message.content.getCardSuit(),
                            message.content.getCardNumber()
                        )
                    }
                    if (GameManager.TABLE_CARD3 == null) {
                        GameManager.TABLE_CARD3 = Card(
                            message.content.getCardSuit(),
                            message.content.getCardNumber()
                        )
                    }
                }

                updateRoomCards()
            }
            PLAYER_MAKE_BET -> TODO()
            PLAYER_MAKE_CHECK -> TODO()
            PLAYER_MAKE_RISE -> TODO()
            PLAYER_MAKE_FOLD -> TODO()
            PAYMENT_TO_PLAYER -> TODO()
            NEXT_STEP_OF_ROUND -> TODO()
            ROUND_END -> TODO()
            OK -> TODO()
            FAIL -> TODO()
            else -> {
                TODO()
            }
        }
    }

    @SuppressLint("NewApi")
    fun drawCardRequest() {
        roomViewModel.sendMessage(
            SocketMessage(
                MessageType.DRAW_CARD,
                MessageContent(Utilities.currentRoomCode),
                Utilities.USER_LOGIN,
                LocalDateTime.now(),
                Utilities.HOST_ADDRESS
            )
        )
    }

    private fun updateRoomCards() {
        val player1 = GameManager.playersMap.get(Utilities.USER_LOGIN)

        roomView.findViewById<ImageView>(R.id.player1Card1).setImageResource(
            Utilities.cardsMap.get(player1?.card1)!!
        )

        roomView.findViewById<ImageView>(R.id.player1Card2).setImageResource(
            Utilities.cardsMap.get(player1?.card2)!!
        )

    }
}