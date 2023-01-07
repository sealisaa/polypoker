package com.example.polypoker.websocket.stomp;

public enum MessageType {
    PLAYER_READY_SET(5),
    PLAYER_ROOM_EXIT(6),
    ROUND_BEGIN(10),
    DRAW_CARD(11),
    PLAYER_MAKE_BET(12),
    PLAYER_MAKE_CHECK(13),
    PLAYER_MAKE_RISE(14),
    PLAYER_MAKE_FOLD(15),
    PAYMENT_TO_PLAYER(16),
    NEXT_STEP_OF_ROUND(17),
    ROUND_END(18),
    OK(200),
    FAIL(500);

    private int value;

    MessageType(int value) {
        this.value = value;
    }
}
