package com.example.polypoker.websocket.stomp.messages

class PaymentToPlayerMessageContent (
    roomCode: Int,
    private var paymentValue: Int
) : MessageContent(roomCode) {

}