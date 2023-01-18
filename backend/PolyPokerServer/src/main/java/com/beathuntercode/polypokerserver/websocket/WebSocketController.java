package com.beathuntercode.polypokerserver.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beathuntercode.polypokerserver.database.model.user.UserDao;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatisticDao;

@RestController
@RequestMapping("/room")
public class WebSocketController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserStatisticDao userStatisticDao;

    private MessageHandler messageHandler;

    private SimpMessagingTemplate simpMessagingTemplate;
    public WebSocketController(SimpMessagingTemplate simpleMessageTemplate) {
        this.simpMessagingTemplate = simpleMessageTemplate;
        messageHandler = new MessageHandler();
    }

    @MessageMapping("/socket")
    @SendTo("/room/user")
    public SocketMessage sendMessage(@Payload SocketMessage message) {
        return messageHandler.handleMessage(message, userDao, userStatisticDao);
    }

}
