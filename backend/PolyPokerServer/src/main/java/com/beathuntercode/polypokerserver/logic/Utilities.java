package com.beathuntercode.polypokerserver.logic;

import com.beathuntercode.polypokerserver.database.controller.RoomController;

public class Utilities {

    public static RoomsController roomsController = new RoomsController();

    public static int getRndIntInRange(int min, int max){
        return (int) (Math.random()*((max-min)+1))+min;
    }
}
