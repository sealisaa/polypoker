package com.beathuntercode.polypokerserver.websocket;

import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.CardNumber;
import com.beathuntercode.polypokerserver.logic.CardSuit;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class MessageContent {

    private Integer roomCode;

    private String userLogin;
    private int moneyValue;

    private CardSuit cardSuit;
    private CardNumber cardNumber;

    public MessageContent() {
    }


    /**
     * For MessageType.
     *      PLAYER_READY_SET(5),
     *      PLAYER_ROOM_EXIT(6),
     *      ROUND_BEGIN(10),
     *      PLAYER_MAKE_CHECK(13),
     *      PLAYER_MAKE_FOLD(15),
     *      NEXT_STEP_OF_ROUND(17),
     *      ROUND_END(18),
     *      OK(200),
     *      FAIL(500)
     */
    public MessageContent(Integer roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * For MessageType.
     *      PLAYER_MAKE_BET(12),
     *      PLAYER_MAKE_RISE(14),
     *      PAYMENT_TO_PLAYER(16)
     */
    public MessageContent(Integer roomCode, int moneyValue) {
        this.roomCode = roomCode;
        this.moneyValue = moneyValue;
    }

    /**
     * For MessageType.
     *      DRAW_CARD(11)
     */
    public MessageContent(Integer roomCode, String userLogin, CardSuit cardSuit, CardNumber cardNumber) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
        this.cardSuit = cardSuit;
        this.cardNumber = cardNumber;
    }


    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
    }

    public int getMoneyValue() {
        return moneyValue;
    }

    public void setMoneyValue(int moneyValue) {
        this.moneyValue = moneyValue;
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public void setCardSuit(CardSuit cardSuit) {
        this.cardSuit = cardSuit;
    }

    public CardNumber getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(CardNumber cardNumber) {
        this.cardNumber = cardNumber;
    }
}
