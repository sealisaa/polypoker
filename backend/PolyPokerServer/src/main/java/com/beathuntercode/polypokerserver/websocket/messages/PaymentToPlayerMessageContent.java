package com.beathuntercode.polypokerserver.websocket.messages;

public class PaymentToPlayerMessageContent extends MessageContent {

    private Integer paymentValue;

    public PaymentToPlayerMessageContent(int roomCode, Integer paymentValue) {
        super(roomCode);
        this.paymentValue = paymentValue;
    }

    public Integer getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Integer paymentValue) {
        this.paymentValue = paymentValue;
    }
}
