package com.beathuntercode.polypokerserver.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class WebSocketController {

    private MessageHandler messageHandler;

    private SimpMessagingTemplate simpleMessageTemplate;
    public WebSocketController(SimpMessagingTemplate simpleMessageTemplate) {
        this.simpleMessageTemplate = simpleMessageTemplate;
        messageHandler = new MessageHandler();
    }

    @MessageMapping("/socket")
    @SendTo("/room/user")
    public SocketMessage sendMessage(@Payload SocketMessage message) {
        return messageHandler.handleMessage(message);
    }

}
