package com.example.polypoker.websocket.stomp.messages

import com.example.polypoker.model.CardNumber
import com.example.polypoker.model.CardSuit

class DrawCardMessageContent(
    roomCode: Int,
    private var cardSuit: CardSuit,
    private var cardNumber: CardNumber
) : MessageContent(roomCode) {


}