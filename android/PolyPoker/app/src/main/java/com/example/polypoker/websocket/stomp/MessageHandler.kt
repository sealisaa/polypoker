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
import com.example.polypoker.model.*
import com.example.polypoker.websocket.stomp.MessageType.*
import java.time.LocalDateTime

class MessageHandler(
    private var roomViewModel: WebSocketViewModel
) {

    private lateinit var roomView: View

    fun handleMessage(message: SocketMessage) {

            roomView = Utilities.currentRoomView

            when (message.messageType) {
                PLAYER_ROOM_JOIN -> playerRoomJoin(message)
                PLAYER_READY_SET -> TODO()
                PLAYER_ROOM_EXIT -> playerRoomExit(message)
                ROUND_BEGIN -> beginRound(message)
                DRAW_CARD -> drawCard(message)
                PLAYER_MAKE_BET -> TODO()
                PLAYER_MAKE_CHECK -> TODO()
                PLAYER_MAKE_RISE -> TODO()
                PLAYER_MAKE_FOLD -> TODO()
                PAYMENT_TO_PLAYER -> TODO()
                NEXT_STEP_OF_ROUND -> nextStepOfRound(message)
                ROUND_END -> TODO()
                OK -> {
                }
                FAIL -> TODO()
                CHECK_ROOM_PLAYERS -> {
                    if (Utilities.currentRoom.playersMap.size <= 1) checkRoomPlayers(message)
                }
                else -> {
                    TODO()
                }
            }

    }

    private fun playerRoomExit(message: SocketMessage) {
        if (message.content.getUserLogin() != Utilities.USER_LOGIN) {
            when (Utilities.currentRoom.playersMap.size) {
                2 -> Utilities.currentRoom.deleteSecondPlayer(message)
                3 -> Utilities.currentRoom.deleteThirdPlayer(message)
                4 -> Utilities.currentRoom.deleteFourthPlayer(message)
                5 -> Utilities.currentRoom.deleteFifthPlayer(message)
                6 -> Utilities.currentRoom.deleteSixthPlayer(message)
            }
        }
    }


    @SuppressLint("NewApi")
    private fun playerRoomJoin(message: SocketMessage) {
        if (Utilities.isPlaying) {
            if (message.content.getUserLogin() != Utilities.USER_LOGIN) {
                when (Utilities.currentRoom.playersMap.size) {
                    1 -> Utilities.currentRoom.addSecondPlayer(message)
                    2 -> Utilities.currentRoom.addThirdPlayer(message)
                    3 -> Utilities.currentRoom.addFourthPlayer(message)
                    4 -> Utilities.currentRoom.addFifthPlayer(message)
                    5 -> Utilities.currentRoom.addSixthPlayer(message)
                }
            }
        }
        else {
            if (message.content.getUserLogin() == Utilities.USER_LOGIN) {
                Utilities.isPlaying = true
                Utilities.currentRoom = Room(message.content.getRoomCode()!!, 10, 20)
                Utilities.currentRoom.playersMap.clear()
                Utilities.currentRoom.addFirstPlayer(message)

                roomViewModel.sendMessage(
                    SocketMessage(
                        MessageType.CHECK_ROOM_PLAYERS,
                        MessageContent(Utilities.currentRoomCode),
                        Utilities.USER_LOGIN,
                        LocalDateTime.now(),
                        Utilities.HOST_ADDRESS
                    )
                )
            }
        }
    }

    private fun beginRound(message: SocketMessage) {
        GameManager.currentGameState = GameState.BLINDS
        nextStepOfRound(message)
        roomView.findViewById<Button>(R.id.readyButton).visibility = View.INVISIBLE
        roomView.findViewById<Button>(R.id.notReadyButton).visibility = View.INVISIBLE
        roomView.findViewById<TextView>(R.id.bankMoneyCount).text = "0"

    }

    private fun nextStepOfRound(message: SocketMessage) {
        GameManager.nextGameState()
        when (GameManager.currentGameState) {
            GameState.BLINDS -> TODO()
            GameState.PREFLOP -> {
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
            GameState.FLOP -> {
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
            GameState.TERN -> TODO()
            GameState.RIVER -> TODO()
            GameState.SHOWDOWN -> TODO()
            else -> TODO()
        }
    }

    private fun drawCard(message: SocketMessage) {
        val player = GameManager.playersMap[message.content.getUserLogin()]
        if (player != null) {
            when {
                player?.card1 == null -> {
                    player?.card1 = Card(
                        message.content.getCardSuit(),
                        message.content.getCardNumber()
                    )
                    if (player.login == Utilities.USER_LOGIN) {
                        updateRoomCard(R.id.player1Card1, player?.card1!!)
                    }
                    else {
                        updateRoomCard(R.id.player2Card1, Card())
                    }
                }
                player.card2 == null -> {
                    player.card2 = Card(
                        message.content.getCardSuit(),
                        message.content.getCardNumber()
                    )
                    if (player.login == Utilities.USER_LOGIN) {
                        updateRoomCard(R.id.player1Card2, player?.card2!!)
                    }
                    else {
                        updateRoomCard(R.id.player2Card2, Card())
                    }
                }
            }
        }
        else {
            when {
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

    @SuppressLint("NewApi")
    private fun checkRoomPlayers(message: SocketMessage) {
        val roomPlayersList: List<Player> = message.content.getRoomPlayersList()!!
        for (player: Player in roomPlayersList) {
            if (player.login != Utilities.USER_LOGIN && player.login != null) {
                val playerRoomJoinMessage = SocketMessage(
                    PLAYER_ROOM_JOIN,
                    MessageContent(
                        message.content.getRoomCode(),
                        player.login,
                        player.name,
                        player.cash
                    ),
                    Utilities.USER_LOGIN,
                    LocalDateTime.now(),
                    Utilities.HOST_ADDRESS
                )
                playerRoomJoin(playerRoomJoinMessage)
            }
        }
    }
}