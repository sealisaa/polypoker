package com.beathuntercode.polypokerserver.database.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatisticDao;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserStatisticController {

    @Autowired
    private UserStatisticDao userStatisticDao;

    @GetMapping("/user/{userLogin}/get-statistic")
    public UserStatistic getUserStatistic(@PathVariable String userLogin) {
        return userStatisticDao.getUserStatistic(userLogin);
    }

    @PostMapping("/user/{userLogin}/save-statistic")
    public UserStatistic saveUserStatistic(@PathVariable String userLogin, @RequestBody UserStatistic newUserStatistic) {
        UserStatistic userStatistic = userStatisticDao.getUserStatistic(userLogin);
        if (userStatistic != null) {
            userStatisticDao.delete(userStatistic);
        }
        else {
            userStatistic = new UserStatistic();
        }
        userStatistic.setLogin(newUserStatistic.getLogin());
        userStatistic.setTotalEarn(newUserStatistic.getTotalEarn());
        userStatistic.setWinGames(newUserStatistic.getWinGames());
        userStatistic.setTotalGamesPlayed(newUserStatistic.getTotalGamesPlayed());
        userStatistic.setCurrentCoinsCount(newUserStatistic.getCurrentCoinsCount());

        return userStatisticDao.saveUserStatistic(userStatistic);
    }
}
