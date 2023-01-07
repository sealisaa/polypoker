package com.example.polypoker.websocket.stomp

import com.example.polypoker.websocket.stomp.MessageType.*

class MessageHandler {

    private fun handleMessage(message: SocketMessage) {
        when (message.messageType) {
            PLAYER_READY_SET -> TODO()
            PLAYER_ROOM_EXIT -> TODO()
            ROUND_BEGIN -> TODO()
            DRAW_CARD -> TODO()
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

}