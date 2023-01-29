package com.beathuntercode.polypokerserver.websocket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.beathuntercode.polypokerserver.database.model.user.User;
import com.beathuntercode.polypokerserver.database.model.user.UserDao;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatisticDao;
import com.beathuntercode.polypokerserver.logic.Card;
import com.beathuntercode.polypokerserver.logic.GameState;
import com.beathuntercode.polypokerserver.logic.Player;
import com.beathuntercode.polypokerserver.logic.Room;
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
            case PLAYER_MUST_MAKE_BET -> {
                return playerMustMakeBet(message, room);
            }
            case    PLAYER_MAKE_CHECK -> {
                return playerMakeCheck(message, room);
            }
            case PLAYER_MAKE_FOLD -> {
                return playerMakeFold(message, room);
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
            case WHO_IS_SMALL_BLIND -> {
                return whoIsSmallBlind(message, room);
            }
            case WHO_IS_BIG_BLIND -> {
                return whoIsBigBlind(message, room);
            }
            case DRAW_CARD -> {
                if (    room.getGameManager().getGameState() == GameState.FLOP ||
                        room.getGameManager().getGameState() == GameState.TERN ||
                        room.getGameManager().getGameState() == GameState.RIVER
                ) {
                    return drawOpenCard(message, room);
                }
                return drawCard(message, room);
            }
            case    PLAYER_MAKE_BET,
                    PLAYER_MAKE_RAISE -> {
                return playerMakeBet(message, room, userDao, userStatisticDao);
            }
            case WINNER_PLAYER -> {
                return winnerPlayer(message, room, userDao, userStatisticDao);
            }
            case IS_NEXT_STEP_OF_ROUND -> {
                return isNextStepOfRound(message, room);
            }
            case NEXT_STEP_OF_ROUND -> {
                return nextStepOfRound(message, room);
            }
            case ROUND_END -> {
                //TODO()
            }
            case SOCKET_DISCONNECT -> {
                room.removePlayerFromRoom(message.getAuthor());
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

    private SocketMessage winnerPlayer(SocketMessage message, Room room, UserDao userDao, UserStatisticDao userStatisticDao) {
        if (message.getAuthor().equals(room.getGameManager().getWinnerPlayer().getLogin())) {
            updateStatsForWinner(room.getGameManager().getWinnerPlayer(), room.getGameManager().getBank(), userDao, userStatisticDao);
        }
        return new SocketMessage(
                message.getMessageType(),
                new MessageContent(
                        message.getContent().getRoomCode(),
                        room.getGameManager().getWinnerPlayer().getLogin(),
                        room.getGameManager().getBank()
                ),
                message.getReceiver(),
                message.getAuthor()
        );
    }

    private SocketMessage isNextStepOfRound(SocketMessage message, Room room) {
        room.getGameManager().changeGameStateToNext();
        return new SocketMessage(
                MessageType.NEXT_STEP_OF_ROUND,
                new MessageContent(
                        message.getContent().getRoomCode(),
                        room.getGameManager().getGameState(),
                        room.getGameManager().getBank()
                ),
                message.getReceiver(),
                message.getAuthor()
        );
    }

    private SocketMessage playerMustMakeBet(SocketMessage message, Room room) {
        List<Player> playersList = room.getPlayersMap().values().stream().toList();
        List<Integer> playersStakesList = new ArrayList<>();
        List<Player> playersWithoutCheckAndFoldList = new ArrayList<>();
        for (Player player : playersList) {
            if (!player.isFold()) {
                if (!player.isCheck()) {
                    playersStakesList.add(player.getCurrentStake());
                    playersWithoutCheckAndFoldList.add(player);
                }
            }
        }
        if (playersWithoutCheckAndFoldList.size() != 0) {
            int maxCurrentStake = playersStakesList.stream().max(Comparator.naturalOrder()).get();
            if (maxCurrentStake == 0) { // если нужна ставка в начале этапа игры
                return new SocketMessage(
                        message.getMessageType(),
                        new MessageContent(
                                message.getContent().getRoomCode(),
                                playersWithoutCheckAndFoldList.get(0).getLogin(),
                                maxCurrentStake - playersWithoutCheckAndFoldList.get(0).getCurrentStake()
                        ),
                        message.getReceiver(),
                        message.getAuthor()
                );
            } else { // если нужна ставка в процессе этапа игры
                for (Player player : playersWithoutCheckAndFoldList) {
                    if (player.getCurrentStake() < maxCurrentStake) {
                        if (player.isMustMakeBet()) {
                            player.setMustMakeBet(false);
                            return new SocketMessage(
                                    message.getMessageType(),
                                    new MessageContent(
                                            message.getContent().getRoomCode(),
                                            player.getLogin(),
                                            maxCurrentStake - player.getCurrentStake()
                                    ),
                                    message.getReceiver(),
                                    message.getAuthor()
                            );
                        } else {
                            return new SocketMessage(
                                    MessageType.OK,
                                    new MessageContent(
                                            message.getContent().getRoomCode()
                                    ),
                                    message.getReceiver(),
                                    message.getAuthor()
                            );
                        }
                    }
                }
            }
        }

        return nextStepOfRound(message, room);
    }

    private SocketMessage playerMakeFold(SocketMessage message, Room room) {
        room.getPlayersMap().get(message.getContent().getUserLogin()).setFold(true);
        return new SocketMessage(
                message.getMessageType(),
                new MessageContent(
                        message.getContent().getRoomCode(),
                        message.getContent().getUserLogin()
                ),
                message.getReceiver(),
                message.getAuthor()
        );
    }

    private SocketMessage playerMakeCheck(SocketMessage message, Room room) {
        room.getPlayersMap().get(message.getContent().getUserLogin()).setCheck(true);
        return new SocketMessage(
                message.getMessageType(),
                new MessageContent(
                        message.getContent().getRoomCode(),
                        message.getContent().getUserLogin()
                ),
                message.getReceiver(),
                message.getAuthor()
        );
    }

    private void updateStatsForWinner(Player player, int moneyValue, UserDao userDao, UserStatisticDao userStatisticDao) {
        User user = userDao.getUserByLogin(player.getLogin());
        UserStatistic oldUserStatistic = userStatisticDao.getUserStatistic(user.getLogin());

        UserStatistic newUserStatistic = new UserStatistic();
        newUserStatistic.setLogin(user.getLogin());
        newUserStatistic.setCurrentCoinsCount(oldUserStatistic.getCurrentCoinsCount() + moneyValue);
        newUserStatistic.setTotalEarn(oldUserStatistic.getTotalEarn() + moneyValue);
        newUserStatistic.setWinGames(oldUserStatistic.getWinGames() + 1);
        newUserStatistic.setTotalGamesPlayed(oldUserStatistic.getTotalGamesPlayed() + 1);

        userStatisticDao.updateUserStatistic(
                newUserStatistic.getLogin(),
                newUserStatistic.getTotalGamesPlayed(),
                newUserStatistic.getWinGames(),
                newUserStatistic.getCurrentCoinsCount(),
                newUserStatistic.getTotalEarn()
        );
    }

    private SocketMessage nextStepOfRound(SocketMessage incomingMessage, Room room) {
        List<Player> roomPlayersList = room.getPlayersMap().values().stream().toList();
        for (Player player : roomPlayersList) {
            room.getGameManager().increaseBank(player.getCurrentStake());
            player.setCurrentStake(0);
        }
        room.getGameManager().changeGameStateToNext();
        switch (room.getGameManager().getGameState()) {
            case FLOP, TERN, RIVER, SHOWDOWN -> {
                return new SocketMessage(
                        MessageType.NEXT_STEP_OF_ROUND,
                        new MessageContent(
                                incomingMessage.getContent().getRoomCode(),
                                room.getGameManager().getGameState(),
                                room.getGameManager().getBank()
                        ),
                        incomingMessage.getReceiver(),
                        incomingMessage.getAuthor()
                );
            }
            default -> {
                return failMessage(incomingMessage);
            }
        }
    }

    private SocketMessage drawOpenCard(SocketMessage message, Room room) {
        room.getGameManager().incrementTimesPlayerAskedForFaceUps(message.getAuthor());
        return new SocketMessage(
                MessageType.DRAW_CARD,
                new MessageContent(
                        message.getContent().getRoomCode(),
                        message.getContent().getUserLogin(),
                        room.getGameManager().getFaceUp()
                ),
                message.getReceiver(),
                message.getAuthor()
        );
    }

    private SocketMessage playerMakeBet(SocketMessage incomingMessage, Room room, UserDao userDao, UserStatisticDao userStatisticDao) {
        for (Player player : room.getPlayersMap().values()) {
            player.setCheck(false);
        }
        room.getPlayersMap().get(incomingMessage.getContent().getUserLogin()).increaseStake(
                incomingMessage.getContent().getMoneyValue()
        );
        decreaseUsersCurrentCoinsCount(
                userDao.getUserByLogin(incomingMessage.getContent().getUserLogin()),
                incomingMessage.getContent().getMoneyValue(),
                userStatisticDao
        );
        room.getPlayersMap().get(incomingMessage.getContent().getUserLogin()).setMustMakeBet(true);
        return new SocketMessage(
                incomingMessage.getMessageType(),
                new MessageContent(
                        incomingMessage.getContent().getRoomCode(),
                        incomingMessage.getContent().getUserLogin(),
                        incomingMessage.getContent().getBetType(),
                        incomingMessage.getContent().getMoneyValue()
                ),
                incomingMessage.getReceiver(),
                incomingMessage.getAuthor()

        );

    }

    private void decreaseUsersCurrentCoinsCount(User user, int moneyValue, UserStatisticDao userStatisticDao) {
        UserStatistic oldUserStatistic = userStatisticDao.getUserStatistic(user.getLogin());

        UserStatistic newUserStatistic = new UserStatistic();
        newUserStatistic.setLogin(user.getLogin());
        newUserStatistic.setCurrentCoinsCount(oldUserStatistic.getCurrentCoinsCount() - moneyValue);
        newUserStatistic.setTotalEarn(oldUserStatistic.getTotalEarn());
        newUserStatistic.setWinGames(oldUserStatistic.getWinGames());
        newUserStatistic.setTotalGamesPlayed(oldUserStatistic.getTotalGamesPlayed());

        userStatisticDao.updateUserCurrentCoinsCount(
                newUserStatistic.getLogin(),
                newUserStatistic.getCurrentCoinsCount()

        );
    }

    private SocketMessage whoIsBigBlind(SocketMessage incomingMessage, Room room) {
        SocketMessage outcomingMessage = null;
        if (!room.isBigBlindSet()) {
            List<Player> roomPlayersList = room.getPlayersMap().values().stream().toList();
            roomPlayersList.get(0).setBigBlind(true);
            for (int i = 0; i < roomPlayersList.size(); i++) {
                if (!roomPlayersList.get(i).isBigBlind()) {
                    roomPlayersList.get(i).setBigBlind(true);
                    room.setBigBlindPlayer(roomPlayersList.get(i));
                    if (i == 0) {
                        roomPlayersList.get(roomPlayersList.size() - 1).setBigBlind(false);
                    } else {
                        roomPlayersList.get(i - 1).setBigBlind(false);
                    }
                    room.setBigBlindSet(true);
                    outcomingMessage = new SocketMessage(
                            incomingMessage.getMessageType(),
                            new MessageContent(
                                    incomingMessage.getContent().getRoomCode(),
                                    roomPlayersList.get(i).getLogin()
                            ),
                            incomingMessage.getReceiver(),
                            incomingMessage.getAuthor()
                    );
                    break;
                }
            }
        }
        else {
            outcomingMessage = new SocketMessage(
                    MessageType.OK,
                    new MessageContent(
                            incomingMessage.getContent().getRoomCode(),
                            room.getBigBlindPlayer().getLogin()
                    ),
                    incomingMessage.getReceiver(),
                    incomingMessage.getAuthor()
            );
        }
        return outcomingMessage;
    }

    private SocketMessage whoIsSmallBlind(SocketMessage incomingMessage, Room room) {
        SocketMessage outcomingMessage = null;
        if (!room.isSmallBlindSet()) {
            List<Player> roomPlayersList = room.getPlayersMap().values().stream().toList();
            for (int i = 0; i < roomPlayersList.size(); i++) {
                if (!roomPlayersList.get(i).isSmallBlind()) {
                    roomPlayersList.get(i).setSmallBlind(true);
                    room.setSmallBlindPlayer(roomPlayersList.get(i));
                    if (i == 0) {
                        roomPlayersList.get(roomPlayersList.size() - 1).setSmallBlind(false);
                    } else {
                        roomPlayersList.get(i - 1).setSmallBlind(false);
                    }
                    room.setSmallBlindSet(true);
                    outcomingMessage = new SocketMessage(
                            incomingMessage.getMessageType(),
                            new MessageContent(
                                    incomingMessage.getContent().getRoomCode(),
                                    roomPlayersList.get(i).getLogin()
                            ),
                            incomingMessage.getReceiver(),
                            incomingMessage.getAuthor()
                    );
                    break;
                }
            }
        }
        else {
            outcomingMessage = new SocketMessage(
                    MessageType.OK,
                    new MessageContent(
                            incomingMessage.getContent().getRoomCode()
                    ),
                    incomingMessage.getReceiver(),
                    incomingMessage.getAuthor()
            );
        }
        return outcomingMessage;
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
            Utilities.roomsController.roomsMap.get(message.getContent().getRoomCode()).getGameManager().getTimesPlayerAskedForFaceUps().put(user.getLogin(), 0);
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
                    message.getAuthor());
        } else {
            return new SocketMessage(
                    MessageType.OK,
                    new MessageContent(message.getContent().getRoomCode()),
                    message.getReceiver(),
                    message.getAuthor()
            );
        }

    }

    private SocketMessage drawCard(SocketMessage message, Room room) {
        Card randomCard1 = room.getGameManager().dealRandomCard();
        Card randomCard2 = room.getGameManager().dealRandomCard();
        if (message.getContent().getUserLogin() != null) {
            if (    room.getPlayersMap().get(message.getContent().getUserLogin()).getCard1() == null ||
                    room.getPlayersMap().get(message.getContent().getUserLogin()).getCard2() == null) {
                room.getPlayersMap().get(message.getContent().getUserLogin()).setCard1(randomCard1);
                room.getPlayersMap().get(message.getContent().getUserLogin()).setCard2(randomCard2);
            }
            return new SocketMessage(
                    MessageType.DRAW_CARD,
                    new MessageContent(
                            message.getContent().getRoomCode(),
                            message.getContent().getUserLogin(),
                            new ArrayList<Card>(Arrays.asList(
                                    room.getPlayersMap().get(message.getContent().getUserLogin()).getCard1(),
                                    room.getPlayersMap().get(message.getContent().getUserLogin()).getCard2())
                            )
                    ),
                    message.getReceiver(),
                    message.getAuthor()
            );
        } else {
            return failMessage(message);
        }
    }


    private SocketMessage playerRoomExit(SocketMessage message, Room room) {
        room.removePlayerFromRoom(message.getAuthor());
        return new SocketMessage(
                MessageType.PLAYER_ROOM_EXIT,
                new MessageContent(message.getContent().getRoomCode(), message.getContent().getUserLogin()),
                message.getReceiver(),
                message.getAuthor()
        );
    }

    private SocketMessage okMessage(SocketMessage message) {
        return new SocketMessage(
                MessageType.OK,
                new MessageContent(message.getContent().getRoomCode()),
                message.getReceiver(),
                message.getAuthor()
        );
    }

    private SocketMessage failMessage(SocketMessage message) {
        return new SocketMessage(
                MessageType.FAIL,
                new MessageContent(message.getContent().getRoomCode()),
                message.getReceiver(),
                message.getAuthor()
        );
    }

}
