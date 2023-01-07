package com.example.polypoker.websocket.stomp.messages

class PaymentToPlayerMessageContent (
    private var paymentValue: Int
) : MessageContent() {

}