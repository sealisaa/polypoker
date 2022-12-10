package com.example.pokertest;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;

@Controller
public class MainController {

    // Stub; do not show during presentation. TODO: implement proper database w/ player balance
    HashMap<String, String> userDatabase = new HashMap<>();

    @MessageMapping("/auth_menu")
    @SendTo("/topic/auth")
    public AuthAnswer authenticate(AuthMessage message) throws Exception {
        if (userDatabase.get(message.getUsername()) == null) {
            return AuthAnswer.INCORRECTLOGIN;
        } else if (Objects.equals(userDatabase.get(message.getUsername()), message.getPassword())) {
            return AuthAnswer.AUTHSUCCESS;
        } else {
            return AuthAnswer.INCORRECTPASSWORD;
        }
    }

    @MessageMapping("/auth_menu")
    @SendTo("/topic/registration")
    public RegMessage register(AuthMessage message) throws Exception {
        if (userDatabase.get(message.getUsername()) != null) {
            return RegMessage.LOGINTAKEN;
        } else {
            userDatabase.put(message.getUsername(), message.getPassword());
            return RegMessage.REGSUCCESS;
        }
    }
}