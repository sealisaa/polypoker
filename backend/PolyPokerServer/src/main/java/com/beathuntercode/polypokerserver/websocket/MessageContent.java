package com.beathuntercode.polypokerserver.websocket;

import java.util.ArrayList;
import java.util.List;

import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.GameState;
import com.beathuntercode.polypokerserver.logic.Player;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class MessageContent {

    private Integer roomCode;
    private GameState gameState;
    private String userLogin;
    private String userName;
    private BetType betType;
    private int moneyValue;
    private int roomMinBlind;
    private List<Player> roomPlayersList;
    private List<Card> cardsList;
    private int playerAvatarNumber;

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
     *      NEXT_STEP_OF_ROUND
     */
    public MessageContent(Integer roomCode, int bankValue) {
        this.roomCode = roomCode;
        this.moneyValue = bankValue;
    }


    /**
     * For MessageType.
     *      PLAYER_ROOM_JOIN
     *
     * Client uses this constructor to send Card request to server
     */
    public MessageContent(Integer roomCode, String userLogin, String userName, Integer moneyValue, Integer playerAvatarNumber, Integer roomMinBlind) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
        this.userName = userName;
        this.moneyValue = moneyValue;
        this.playerAvatarNumber = playerAvatarNumber;
        this.roomMinBlind = roomMinBlind;
    }

    /**
     * For MessageType.
     *      PLAYER_READY_SET,
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
     *      IS_NEXT_STEP_OF_ROUND,
     */
    public MessageContent(Integer roomCode, GameState gameState, int moneyValue) {
        this.roomCode = roomCode;
        this.gameState = gameState;
        this.moneyValue = moneyValue;
    }

    /**
     * For MessageType.
     *
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
     *      PLAYER_MAKE_BET
     *
     */
    public MessageContent(Integer roomCode, String userLogin, BetType betType, int moneyValue) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
        this.betType = betType;
        this.moneyValue = moneyValue;
    }

    /**
     * For MessageType.
     *      DRAW_CARD
     *      PLAYER_MAKE_CHECK,
     *      PLAYER_MAKE_FOLD,
     *      PLAYER_ROOM_EXIT,
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
    public MessageContent(Integer roomCode, String userLogin, List<Card> cardsList) {
        this.roomCode = roomCode;
        this.userLogin = userLogin;
        this.cardsList = cardsList;
    }

    /**
     * For MessageType.
     *      DRAW_CARD
     *
     * Server uses this constructor to send Card to client for table in room
     */
    public MessageContent(Integer roomCode, ArrayList<Card> cardsList) {
        this.roomCode = roomCode;
        this.cardsList = cardsList;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
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

    public BetType getBetType() {
        return betType;
    }

    public void setBetType(BetType betType) {
        this.betType = betType;
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

    public List<Card> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<Card> cardsList) {
        this.cardsList = cardsList;
    }

    public int getPlayerAvatarNumber() {
        return playerAvatarNumber;
    }

    public void setPlayerAvatarNumber(int playerAvatarNumber) {
        this.playerAvatarNumber = playerAvatarNumber;
    }

    public int getRoomMinBlind() {
        return roomMinBlind;
    }

    public void setRoomMinBlind(int roomMinBlind) {
        this.roomMinBlind = roomMinBlind;
    }

    @Override
    public String toString() {
        return "MessageContent{" +
                "roomCode=" + roomCode +
                ", gameState=" + gameState +
                ", userLogin='" + userLogin + '\'' +
                ", userName='" + userName + '\'' +
                ", betType=" + betType +
                ", moneyValue=" + moneyValue +
                ", roomMinBlind=" + roomMinBlind +
                ", roomPlayersList=" + roomPlayersList +
                ", cardsList=" + cardsList +
                ", playerAvatarNumber=" + playerAvatarNumber +
                '}';
    }
}
