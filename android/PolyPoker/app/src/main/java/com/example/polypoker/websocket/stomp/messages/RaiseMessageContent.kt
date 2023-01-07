package com.example.polypoker.websocket.stomp.messages

class RaiseMessageContent (
    roomCode: Int,
    private var raiseValue: Int
) : MessageContent(roomCode) {

}