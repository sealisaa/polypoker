package com.example.polypoker.websocket.stomp

import com.example.polypoker.model.CardNumber

import com.example.polypoker.model.CardSuit




open class MessageContent {
    private var roomCode: Int? = null

    private var userLogin: String? = null

    private var moneyValue: Int? = null

    private var cardSuit: CardSuit? = null

    private var cardNumber: CardNumber? = null

    constructor() {

    }

    constructor (
        roomCode: Int?,
    ) {
        this.roomCode = roomCode
    }

    constructor (
        roomCode: Int?,
        moneyValue: Int?,
    ) {
        this.roomCode = roomCode
        this.moneyValue = moneyValue
    }

    constructor (
        roomCode: Int?,
        userLogin: String?,
    ) {
        this.roomCode = roomCode
        this.userLogin = userLogin
    }

    constructor (
        roomCode: Int?,
        userLogin: String?,
        cardSuit: CardSuit?,
        cardNumber: CardNumber?
    ) {
        this.roomCode = roomCode
        this.userLogin = userLogin
        this.cardSuit = cardSuit
        this.cardNumber = cardNumber
    }

    constructor (
        roomCode: Int?,
        cardSuit: CardSuit?,
        cardNumber: CardNumber?
    ) {
        this.roomCode = roomCode
        this.cardSuit = cardSuit
        this.cardNumber = cardNumber
    }

    open fun getRoomCode(): Int? {
        return roomCode
    }

    open fun setRoomCode(roomCode: Int?) {
        this.roomCode = roomCode
    }

    open fun getUserLogin(): String? {
        return userLogin
    }

    open fun setUserLogin(userLogin: String?) {
        this.userLogin = userLogin
    }

    open fun getMoneyValue(): Int? {
        return moneyValue
    }

    open fun setMoneyValue(moneyValue: Int?) {
        this.moneyValue = moneyValue
    }

    open fun getCardSuit(): CardSuit? {
        return cardSuit
    }

    open fun setCardSuit(cardSuit: CardSuit?) {
        this.cardSuit = cardSuit
    }

    open fun getCardNumber(): CardNumber? {
        return cardNumber
    }

    open fun setCardNumber(cardNumber: CardNumber?) {
        this.cardNumber = cardNumber
    }

}