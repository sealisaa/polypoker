package com.beathuntercode.polypokerserver.logic;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.beathuntercode.polypokerserver.database.model.user.User;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.websocket.MessageContent;
import com.beathuntercode.polypokerserver.websocket.MessageType;
import com.beathuntercode.polypokerserver.websocket.SocketMessage;
import com.beathuntercode.polypokerserver.websocket.WebSocketController;

public class RoomsController {

    public Map<Integer, Room> roomsMap = new HashMap<>();

    public void createRoom(int roomCode, int minBlind, int minRaise) {
        roomsMap.put(roomCode, new Room(roomCode, minBlind, minRaise));
        System.out.println("------------- CURRENT ROOMS:");
        for (Room room : roomsMap.values()) {
            System.out.println("\t" + room.getRoomCode() + ": minBlind = " + room.getMinBlind() + "; minRaise = " + room.getMinRaise());
        }
    }

    public boolean exitRoom(int roomCode, Player player) {
        if (roomsMap.containsKey(roomCode)) {
            roomsMap.get(roomCode).getPlayersMap().remove(player);
            return true;
        }
        else {
            return false;
        }
    }

}
