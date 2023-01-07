package com.example.polypoker.websocket.stomp.messages

import com.example.polypoker.model.CardNumber
import com.example.polypoker.model.CardSuit

class BetMessageContent (
    roomCode: Int,
    private var betValue: Int
) : MessageContent(roomCode) {

}