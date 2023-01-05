package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.beathuntercode.polypokerserver.database.model.user.User;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatisticDao;

public class RoomsController {

    public Map<Integer, Room> roomsList = new HashMap<>();

    public void createRoom(int roomCode, int minBlind, int minRaise) {
        roomsList.put(roomCode, new Room(roomCode, minBlind, minRaise));
    }

    public void createRoom(Room room) {
        roomsList.put(room.getRoomCode(), room);
    }

    public boolean joinRoom(int roomCode, Player player) {
        if (roomsList.containsKey(roomCode)) {
            roomsList.get(roomCode).getPlayersList().add(player);
            System.out.println(roomsList.get(roomCode).getPlayersList());
            return true;
        }
        else {
            return false;
        }
    }

    public boolean exitRoom(int roomCode, Player player) {
        if (roomsList.containsKey(roomCode)) {
            roomsList.get(roomCode).getPlayersList().remove(player);
            return true;
        }
        else {
            return false;
        }
    }

}
