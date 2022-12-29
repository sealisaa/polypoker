package com.beathuntercode.polypokerserver.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beathuntercode.polypokerserver.database.model.user.User;

public class RoomsController {

    public static Map<Integer, Room> roomsList = new HashMap<Integer, Room>();

    public static void createRoom(int roomCode, int minBlind, int minRaise) {
        roomsList.put(roomCode, new Room(roomCode, minBlind, minRaise));
    }

    public static void joinRoom(int roomCode, User user) {

    }

    public static void exitRoom(int roomCode, User user) {

    }

}
