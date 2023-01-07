package com.example.polypoker.websocket.stomp

import java.time.LocalDateTime
import java.time.ZoneOffset

data class Message(
    var datetime: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC),
    val text: String,
    val author: String,
    var receiver: String? = null
)