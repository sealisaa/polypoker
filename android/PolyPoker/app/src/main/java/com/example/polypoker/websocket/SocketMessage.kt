package com.example.polypoker.websocket

import java.time.LocalDateTime

data class SocketMessage (
    val text: String,
    val author: String,
    val datetime: LocalDateTime,
    var receiver: String? = null
)