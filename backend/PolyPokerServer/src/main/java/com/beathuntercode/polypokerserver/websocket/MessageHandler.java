package com.beathuntercode.polypokerserver.websocket;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import com.beathuntercode.polypokerserver.database.controller.UserController;
import com.beathuntercode.polypokerserver.database.controller.UserStatisticController;
import com.beathuntercode.polypokerserver.database.model.user.User;
import com.beathuntercode.polypokerserver.database.model.user.UserDao;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatisticDao;
import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.GameState;
import com.beathuntercode.polypokerserver.logic.Player;
import com.beathuntercode.polypokerserver.logic.Room;
import com.beathuntercode.polypokerserver.logic.RoomsController;
import com.beathuntercode.polypokerserver.logic.Utilities;

@RestController
public class MessageHandler {

    /**
     * Handles incoming from client SocketMessage and creates SocketMessage answer for client that will send back
     *
     * @param message Incoming SocketMessage from client
     * @return SocketMessage answer for client
     */
    public SocketMessage handleMessage(SocketMessage message, UserDao userDao, UserStatisticDao userStatisticDao) {
        Room room = Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode());
        switch (message.getMessageType()) {
            case PLAYER_MAKE_CHECK,
                    PLAYER_MAKE_FOLD -> {
                return okMessage(message);
            }
            case PLAYER_ROOM_JOIN -> {
                return playerRoomJoin(message, userDao, userStatisticDao);
            }
            case PLAYER_READY_SET -> {
                return playerReadySet(message, room);
            }
            case PLAYER_ROOM_EXIT -> {
                return playerRoomExit(message, room);
            }
            case CHECK_ROOM_PLAYERS -> {
                return checkRoomPlayer(message);
            }
            case ROUND_BEGIN -> {
                //TODO()
            }
            case DRAW_CARD -> {
                return drawCard(message, room);
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
            case SOCKET_DISCONNECT -> {
                room.getPlayersMap().remove(message.getAuthor());
            }
            case OK -> {
                //TODO()
            }
            case FAIL -> {
                //TODO()
            }
            default -> {
                return failMessage(message);
            }
        }
        return null;
    }

    private SocketMessage checkRoomPlayer(SocketMessage message) {
        Room room = Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode());
        List<Player> roomPlayersList = room.getPlayersMap().values().stream().toList();
        return new SocketMessage(
                MessageType.CHECK_ROOM_PLAYERS,
                new MessageContent(
                        message.getContent().getRoomCode(),
                        roomPlayersList
                ),
                message.getReceiver(),
                LocalDateTime.now(),
                message.getAuthor()
        );
    }

    private SocketMessage playerRoomJoin(SocketMessage message, UserDao userDao, UserStatisticDao userStatisticDao) {
        UserStatistic userStatistic = userStatisticDao.getUserStatistic(message.getContent().getUserLogin());
        User user = userDao.getUserByLogin(message.getContent().getUserLogin());
        Player player = new Player(
                message.getContent().getUserLogin(),
                user.getName() + " " + user.getSurname(),
                0,
                userStatistic.getCurrentCoinsCount()
        );
        if (Utilities.roomsController.roomsMap.containsKey(message.getContent().getRoomCode())) {
            Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode()).getPlayersMap().put(user.getLogin(), player);
            System.out.println(Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode()).getPlayersMap());
            return new SocketMessage(
                    MessageType.PLAYER_ROOM_JOIN,
                    new MessageContent(
                            message.getContent().getRoomCode(),
                            user.getLogin(),
                            user.getName() + " " + user.getSurname(),
                            userStatistic.getCurrentCoinsCount()

                    ),
                    message.getReceiver(),
                    LocalDateTime.now(),
                    message.getAuthor()

            );

        } else return failMessage(message);
    }


    private SocketMessage playerReadySet(SocketMessage message, Room room) {
        room.getPlayersMap().get(message.getAuthor()).setReady(
                !room.getPlayersMap().get(message.getAuthor()).isReady()
        );

        if (room.getPlayersMap().entrySet().stream().allMatch(entry -> entry.getValue().isReady())) {
//                    room.getGameManager().shuffleDeck();
            room.getGameManager().setGameState(GameState.BLINDS);
            return new SocketMessage(
                    MessageType.ROUND_BEGIN,
                    new MessageContent(message.getContent().getRoomCode()),
                    message.getReceiver(),
                    LocalDateTime.now(),
                    message.getAuthor());
        } else {
            return new SocketMessage(
                    MessageType.OK,
                    new MessageContent(message.getContent().getRoomCode()),
                    message.getReceiver(),
                    LocalDateTime.now(),
                    message.getAuthor()
            );
        }

    }

    private SocketMessage drawCard(SocketMessage message, Room room) {
        Card randomCard = room.getGameManager().dealRandomCard();
        if (message.getContent().getUserLogin() != null) {
            return new SocketMessage(
                    MessageType.DRAW_CARD,
                    new MessageContent(
                            message.getContent().getRoomCode(),
                            message.getContent().getUserLogin(),
                            randomCard.getCardSuit(),
                            randomCard.getCardNumber()
                    ),
                    message.getReceiver(),
                    LocalDateTime.now(),
                    message.getAuthor()
            );
        } else {
            return new SocketMessage(
                    MessageType.DRAW_CARD,
                    new MessageContent(
                            message.getContent().getRoomCode(),
                            randomCard.getCardSuit(),
                            randomCard.getCardNumber()
                    ),
                    message.getReceiver(),
                    LocalDateTime.now(),
                    message.getAuthor()
            );
        }
    }


    private SocketMessage playerRoomExit(SocketMessage message, Room room) {
        room.getPlayersMap().remove(message.getAuthor());
        return new SocketMessage(
                MessageType.PLAYER_ROOM_EXIT,
                new MessageContent(message.getContent().getRoomCode(), message.getContent().getUserLogin()),
                message.getReceiver(),
                LocalDateTime.now(),
                message.getAuthor()
        );
    }

    private SocketMessage okMessage(SocketMessage message) {
        return new SocketMessage(
                MessageType.OK,
                new MessageContent(message.getContent().getRoomCode()),
                message.getReceiver(),
                LocalDateTime.now(),
                message.getAuthor()
        );
    }

    private SocketMessage failMessage(SocketMessage message) {
        return new SocketMessage(
                MessageType.FAIL,
                new MessageContent(message.getContent().getRoomCode()),
                message.getReceiver(),
                LocalDateTime.now(),
                message.getAuthor()
        );
    }

}
