package com.beathuntercode.polypokerserver.websocket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.security.Principal;
import java.time.LocalDateTime;

@JsonInclude
public class SocketMessage {

    private MessageType messageType;
    private MessageContent content;
    private String author;
    private String receiver;

    public SocketMessage(MessageType messageType, MessageContent content, String author, String receiver) {
        this.messageType = messageType;
        this.content = content;
        this.author = author;
        this.receiver = receiver;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageContent getContent() {
        return content;
    }

    public void setContent(MessageContent content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        return "SocketMessage{" +
                "messageType=" + messageType +
                ", content=" + content +
                ", author='" + author + '\'' +
                ", receiver='" + receiver + '\'' +
                '}';
    }
}
