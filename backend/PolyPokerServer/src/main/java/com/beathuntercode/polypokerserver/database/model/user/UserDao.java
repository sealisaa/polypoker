package com.beathuntercode.polypokerserver.database.model.user;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatistic;
import com.beathuntercode.polypokerserver.database.model.userstatistic.UserStatisticRepository;

@Service
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatisticRepository userStatisticRepository;


    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User save(User u) {
        UserStatistic userStatistic = new UserStatistic();
        userStatistic.setLogin(u.getLogin());
        userStatistic.setCurrentCoinsCount(5000);
        userStatistic.setTotalGamesPlayed(0);
        userStatistic.setWinGames(0);
        userStatistic.setTotalEarn(0);
        userStatisticRepository.save(userStatistic);

        User user = new User();

        user.setLogin(u.getLogin());
        user.setPassword(encoder.encode(u.getPassword()));
        user.setName(u.getName());
        user.setSurname(u.getSurname());

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

    public boolean isUserAuthorized(User user) {
        User dbUser = getUserByLogin(user.getLogin());
        return encoder.matches(user.getPassword(), dbUser.getPassword());
    }
}
