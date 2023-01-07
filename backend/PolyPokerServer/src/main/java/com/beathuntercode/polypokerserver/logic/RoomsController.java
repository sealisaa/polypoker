package com.beathuntercode.polypokerserver.logic;

import java.util.HashMap;
import java.util.Map;

import com.beathuntercode.polypokerserver.database.model.user.User;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;

public class RoomsController {

    public Map<Integer, Room> roomsMap = new HashMap<>();

    public void createRoom(int roomCode, int minBlind, int minRaise) {
        roomsMap.put(roomCode, new Room(roomCode, minBlind, minRaise));
    }

    public void createRoom(Room room) {
        roomsMap.put(room.getRoomCode(), room);
    }

    public boolean joinRoom(int roomCode, User user, UserStatistic userStatistic) {
        Player player = new Player(
                user.getName() + " " + user.getSurname(),
                userStatistic.getCurrentCoinsCount()
        );
        if (roomsMap.containsKey(roomCode)) {
            roomsMap.get(roomCode).getPlayersMap().put(user.getLogin(), player);
            System.out.println(roomsMap.get(roomCode).getPlayersMap());
            return true;
        }
        else {
            return false;
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
