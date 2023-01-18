package com.beathuntercode.polypokerserver.websocket;

public enum MessageType {
    PLAYER_ROOM_JOIN(4),
    PLAYER_READY_SET(5),
    PLAYER_ROOM_EXIT(6),
    CHECK_ROOM_PLAYERS(7),
    ROUND_BEGIN(10),
    WHO_IS_SMALL_BLIND(11),
    WHO_IS_BIG_BLIND(12),
    DRAW_CARD(13),
    PLAYER_MAKE_BET(14),
    PLAYER_MAKE_CHECK(15),
    PLAYER_MAKE_RAISE(16),
    PLAYER_MAKE_FOLD(17),
    PAYMENT_TO_PLAYER(18),
    NEXT_STEP_OF_ROUND(19),
    ROUND_END(20),
    SOCKET_DISCONNECT(100),
    OK(200),
    FAIL(500);


    private int value;

    MessageType(int value) {
        this.value = value;
    }
}
