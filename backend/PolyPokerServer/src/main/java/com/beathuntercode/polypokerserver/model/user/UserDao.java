package com.beathuntercode.polypokerserver.model.user;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.beathuntercode.polypokerserver.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.model.userstatistic.UserStatisticRepository;

@Service
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatisticRepository userStatisticRepository;

    public User save(User user) {
        UserStatistic userStatistic = new UserStatistic();
        userStatistic.setLogin(user.getLogin());
        userStatistic.setCurrentCoinsCount(5000);
        userStatistic.setTotalGamesPlayed(0);
        userStatistic.setWinGames(0);
        userStatistic.setTotalEarn(0);
        userStatisticRepository.save(userStatistic);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        Streamable.of(userRepository.findAll()).forEach(usersList::add);
        return  usersList;
    }

    public User getUserByLogin(String login) {
        return userRepository.findOneUserByLogin(login);
    }

    public void delete(User user) {
        userStatisticRepository.delete(userStatisticRepository.findOneUserStatisticByLogin(user.getLogin()));
        userRepository.delete(user);
    }
}
