package com.example.polypoker.websocket.stomp

import com.example.polypoker.websocket.stomp.messages.MessageContent
import java.time.LocalDateTime

data class SocketMessage (
    val messageType: MessageType,
    val content: MessageContent,
    var author: String,
    var datetime: LocalDateTime,
    var receiver: String? = null
)