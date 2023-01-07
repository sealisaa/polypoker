package com.example.polypoker.websocket.stomp

fun dtoToEntity(dto: SocketMessage) : Message {
    return Message(
        dto.datetime,
        dto.text,
        dto.author,
        dto.receiver
    )
}

fun entityToDto(entity: Message) : SocketMessage {
    return SocketMessage(
        entity.text,
        entity.author,
        entity.datetime,
        entity.receiver
    )
}