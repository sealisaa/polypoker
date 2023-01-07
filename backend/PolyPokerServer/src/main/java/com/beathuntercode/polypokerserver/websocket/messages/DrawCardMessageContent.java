package com.beathuntercode.polypokerserver.websocket.messages;

import java.time.LocalDateTime;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.beathuntercode.polypokerserver.logic.CardNumber;
import com.beathuntercode.polypokerserver.logic.CardSuit;
import com.beathuntercode.polypokerserver.websocket.MessageType;
import com.beathuntercode.polypokerserver.websocket.SocketMessage;

public class DrawCardMessageContent extends MessageContent {

    CardSuit cardSuit;
    CardNumber cardNumber;

    public DrawCardMessageContent(CardSuit cardSuit, CardNumber cardNumber) {
        this.cardSuit = cardSuit;
        this.cardNumber = cardNumber;
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
