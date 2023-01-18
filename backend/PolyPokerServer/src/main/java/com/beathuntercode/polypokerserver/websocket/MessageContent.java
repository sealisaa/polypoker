package com.beathuntercode.polypokerserver.websocket;

import java.util.List;

import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.CardNumber;
import com.beathuntercode.polypokerserver.logic.CardSuit;
import com.beathuntercode.polypokerserver.logic.Player;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class MessageContent {

    private Integer roomCode;
    private String userLogin;
    private String userName;
    private int moneyValue;

    private CardSuit cardSuit;
    private CardNumber cardNumber;

    private List<Player> roomPlayersList;

    public MessageContent() {
    }

    /**
     * For MessageType.
     *      CHECK_ROOM_PLAYERS
     *
     * Client uses this constructor to send Card request to server
     */
    public MessageContent(Integer roomCode, List<Player> roomPlayersList) {
        this.roomCode = roomCode;
        this.roomPlayersList = roomPlayersList;
    }


    /**
     * For MessageType.
     *      PLAYER_ROOM_JOIN
     *
     * Client uses this constructor to send Card request to server
     */
    public MessageContent(Integer roomCode, String userLogin, String userName, Integer moneyValue) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
        this.userName = userName;
        this.moneyValue = moneyValue;
    }

    /**
     * For MessageType.
     *      PLAYER_READY_SET,
     *      PLAYER_ROOM_EXIT,
     *      WHO_IS_SMALL_BLIND,
     *      WHO_IS_BIG_BLIND,
     *      ROUND_BEGIN,
     *      NEXT_STEP_OF_ROUND,
     *      ROUND_END,
     *      OK,
     *      FAIL
     */
    public MessageContent(Integer roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * For MessageType.
     *      PLAYER_MAKE_BET,
     *      PLAYER_MAKE_RAISE,
     *      PAYMENT_TO_PLAYER
     */
    public MessageContent(Integer roomCode, String userLogin, int moneyValue) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
        this.moneyValue = moneyValue;
    }

    /**
     * For MessageType.
     *      DRAW_CARD
     *      PLAYER_MAKE_CHECK,
     *      PLAYER_MAKE_FOLD
     *
     * Client uses this constructor to send Card request to server
     */
    public MessageContent(Integer roomCode, String userLogin) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
    }

    /**
     * For MessageType.
     *      DRAW_CARD
     *
     * Server uses this constructor to send Card to client
     */
    public MessageContent(Integer roomCode, String userLogin, CardSuit cardSuit, CardNumber cardNumber) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
        this.cardSuit = cardSuit;
        this.cardNumber = cardNumber;
    }

    /**
     * For MessageType.
     *      DRAW_CARD
     *
     * Server uses this constructor to send Card to client for table in room
     */
    public MessageContent(Integer roomCode, CardSuit cardSuit, CardNumber cardNumber) {
        this.roomCode = roomCode;
        this.cardSuit = cardSuit;
        this.cardNumber = cardNumber;
    }


    public Integer getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(Integer roomCode) {
        this.roomCode = roomCode;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Player> getRoomPlayersList() {
        return roomPlayersList;
    }

    public void setRoomPlayersList(List<Player> roomPlayersList) {
        this.roomPlayersList = roomPlayersList;
    }
}
