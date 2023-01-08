package com.example.polypoker.websocket.stomp

import android.annotation.SuppressLint
import android.icu.util.UniversalTimeScale
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
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
            PLAYER_ROOM_JOIN -> playerRoomJoin(message)
            PLAYER_READY_SET -> TODO()
            PLAYER_ROOM_EXIT -> TODO()
            ROUND_BEGIN -> beginRound(message)
            DRAW_CARD -> drawCard(message)
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

    private fun playerRoomJoin(message: SocketMessage) {
        if (Utilities.isPlaying) {
            if (message.content.getUserLogin() != Utilities.USER_LOGIN) {

            }
        }
        else {
            Utilities.currentMainMenuView?.findNavController()?.navigate(
                R.id.action_mainMenuFragment_to_roomFragment
            )
        }
    }

    private fun beginRound(message: SocketMessage) {
        roomView.findViewById<Button>(R.id.readyButton).visibility = View.INVISIBLE
        roomView.findViewById<Button>(R.id.notReadyButton).visibility = View.INVISIBLE
        roomView.findViewById<TextView>(R.id.bankMoneyCount).text = "0"

        for (player in GameManager.playersMap.values) {
            if (player.card1 == null) {
                drawCardForPlayerRequest(player)
            }
            if (player.card2 == null) {
                drawCardForPlayerRequest(player)
            }
        }

        if (GameManager.TABLE_CARD1 == null) {
            drawCardForTableRequest()
        }
        if (GameManager.TABLE_CARD2 == null) {
            drawCardForTableRequest()
        }
        if (GameManager.TABLE_CARD3 == null) {
            drawCardForTableRequest()
        }
    }

    private fun drawCard(message: SocketMessage) {
        val player = GameManager.playersMap[message.content.getUserLogin()]
        when {
            player?.card1 == null -> {
                player?.card1 = Card(
                    message.content.getCardSuit(),
                    message.content.getCardNumber()
                )
                updateRoomCard(R.id.player1Card1, player?.card1!!)
            }
            player.card2 == null -> {
                player.card2 = Card(
                    message.content.getCardSuit(),
                    message.content.getCardNumber()
                )
                updateRoomCard(R.id.player1Card2, player?.card2!!)
            }
            GameManager.TABLE_CARD1 == null -> {
                GameManager.TABLE_CARD1 = Card(
                    message.content.getCardSuit(),
                    message.content.getCardNumber()
                )
                updateRoomCard(R.id.tableCard1, GameManager.TABLE_CARD1)
            }
            GameManager.TABLE_CARD2 == null -> {
                GameManager.TABLE_CARD2 = Card(
                    message.content.getCardSuit(),
                    message.content.getCardNumber()
                )
                updateRoomCard(R.id.tableCard2, GameManager.TABLE_CARD2)
            }
            GameManager.TABLE_CARD3 == null -> {
                GameManager.TABLE_CARD3 = Card(
                    message.content.getCardSuit(),
                    message.content.getCardNumber()
                )
                updateRoomCard(R.id.tableCard3, GameManager.TABLE_CARD3)
            }
        }
    }

    @SuppressLint("NewApi")
    fun drawCardForPlayerRequest(player: Player) {
        roomViewModel.sendMessage(
            SocketMessage(
                MessageType.DRAW_CARD,
                MessageContent(Utilities.currentRoomCode, player.login),
                Utilities.USER_LOGIN,
                LocalDateTime.now(),
                Utilities.HOST_ADDRESS
            )
        )
    }

    @SuppressLint("NewApi")
    fun drawCardForTableRequest() {
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


    private fun updateRoomCard(cardResource: Int, card: Card) {
        roomView.findViewById<ImageView>(cardResource).setImageResource(
            Utilities.cardsMap[card]!!
        )
    }
}