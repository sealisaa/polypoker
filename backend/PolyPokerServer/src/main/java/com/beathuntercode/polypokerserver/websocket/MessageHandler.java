package com.beathuntercode.polypokerserver.websocket;

import java.time.LocalDateTime;

import com.beathuntercode.polypokerserver.logic.Room;
import com.beathuntercode.polypokerserver.logic.Utilities;

public class MessageHandler {

    /**
     * Handles incoming from client SocketMessage and creates SocketMessage answer for client that will send back
     * @param message Incoming SocketMessage from client
     * @return SocketMessage answer for client
     */
    public SocketMessage handleMessage(SocketMessage message) {
        switch (message.getMessageType()) {
            case    PLAYER_MAKE_CHECK,
                    PLAYER_MAKE_FOLD -> {
                return new SocketMessage(
                        MessageType.OK,
                        new MessageContent(message.getContent().getRoomCode()),
                        message.getReceiver(),
                        LocalDateTime.now(),
                        message.getAuthor()
                );
            }
            case PLAYER_READY_SET -> {
                Room room = Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode());
                room.getPlayersMap().get(message.getAuthor()).setReady(
                        !room.getPlayersMap().get(message.getAuthor()).isReady()
                );

                return new SocketMessage(
                        MessageType.OK,
                        new MessageContent(message.getContent().getRoomCode()),
                        message.getReceiver(),
                        LocalDateTime.now(),
                        message.getAuthor());

            }
            case PLAYER_ROOM_EXIT -> {
                Room room = Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode());
                room.getPlayersMap().remove(message.getAuthor());
            }
            case ROUND_BEGIN -> {
                //TODO()
            }
            case DRAW_CARD -> {
                //TODO()
            }
            case PLAYER_MAKE_BET -> {
                //TODO()
            }
            case PLAYER_MAKE_RISE -> {
                //TODO()
            }
            case PAYMENT_TO_PLAYER -> {
                //TODO()
            }
            case NEXT_STEP_OF_ROUND -> {
                //TODO()
            }
            case ROUND_END -> {
                //TODO()
            }
            case OK -> {
                //TODO()
            }
            case FAIL -> {
                //TODO()
            }
            default -> {
                return new SocketMessage(
                        MessageType.FAIL,
                        new MessageContent(message.getContent().getRoomCode()),
                        message.getReceiver(),
                        LocalDateTime.now(),
                        message.getAuthor()
                );
            }
        }
        return null;
    }

}
