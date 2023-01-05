package com.beathuntercode.polypokerserver.database.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beathuntercode.polypokerserver.database.model.user.User;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatisticDao;
import com.beathuntercode.polypokerserver.logic.Player;
import com.beathuntercode.polypokerserver.logic.Room;
import com.beathuntercode.polypokerserver.logic.RoomsController;
import com.beathuntercode.polypokerserver.logic.Utilities;

@RestController
public class RoomController {

    @Autowired
    private UserStatisticDao userStatisticDao;

    @PostMapping("/create-room")
    public boolean createRoom(@RequestBody Room room) {
        try {
            Utilities.roomsController.createRoom(room);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/join-room/{roomCode}")
    public boolean joinRoom(@PathVariable Integer roomCode, @RequestBody User user) {
        UserStatistic userStatistic = userStatisticDao.getUserStatistic(user.getLogin());
        Player player = new Player(
                user.getName() + " " + user.getSurname(),
                userStatistic.getCurrentCoinsCount()
        );
        return Utilities.roomsController.joinRoom(roomCode, player);
    }
}
