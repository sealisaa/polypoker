package com.beathuntercode.polypokerserver.websocket;

import java.time.LocalDateTime;

import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.Room;
import com.beathuntercode.polypokerserver.logic.Utilities;

public class MessageHandler {

    /**
     * Handles incoming from client SocketMessage and creates SocketMessage answer for client that will send back
     * @param message Incoming SocketMessage from client
     * @return SocketMessage answer for client
     */
    public SocketMessage handleMessage(SocketMessage message) {
        Room room = Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode());
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
                room.getPlayersMap().get(message.getAuthor()).setReady(
                        !room.getPlayersMap().get(message.getAuthor()).isReady()
                );

                if (room.getPlayersMap().entrySet().stream().allMatch(entry -> entry.getValue().isReady())) {
//                    room.getGameManager().shuffleDeck();
                    return new SocketMessage(
                            MessageType.ROUND_BEGIN,
                            new MessageContent(message.getContent().getRoomCode()),
                            message.getReceiver(),
                            LocalDateTime.now(),
                            message.getAuthor());
                }
                else {
                    return new SocketMessage(
                            MessageType.OK,
                            new MessageContent(message.getContent().getRoomCode()),
                            message.getReceiver(),
                            LocalDateTime.now(),
                            message.getAuthor()
                    );
                }

            }
            case PLAYER_ROOM_EXIT -> {
                room.getPlayersMap().remove(message.getAuthor());
            }
            case ROUND_BEGIN -> {
                //TODO()
            }
            case DRAW_CARD -> {
                Card randomCard = room.getGameManager().dealRandomCard();
                return new SocketMessage(
                        MessageType.DRAW_CARD,
                        new MessageContent(
                                message.getContent().getRoomCode(),
                                message.getAuthor(),
                                randomCard.getCardSuit(),
                                randomCard.getCardNumber()
                        ),
                        message.getReceiver(),
                        LocalDateTime.now(),
                        message.getAuthor()
                );
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
