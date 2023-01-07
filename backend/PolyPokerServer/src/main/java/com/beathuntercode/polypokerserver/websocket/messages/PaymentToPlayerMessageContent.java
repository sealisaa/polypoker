package com.beathuntercode.polypokerserver.websocket.messages;

public class PaymentToPlayerMessageContent extends MessageContent {

    private Integer paymentValue;

    public PaymentToPlayerMessageContent(Integer paymentValue) {
        this.paymentValue = paymentValue;
    }

    public Integer getPaymentValue() {
        return paymentValue;
    }

    public void setPaymentValue(Integer paymentValue) {
        this.paymentValue = paymentValue;
    }
}
