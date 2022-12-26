package com.beathuntercode.polypokerserver.model.userstatistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beathuntercode.polypokerserver.model.user.User;

@Service
public class UserStatisticDao {

    @Autowired
    private UserStatisticRepository repository;

    public UserStatistic saveUserStatistic(UserStatistic userStatistic) {
        return repository.save(userStatistic);
    }

    public UserStatistic getUserStatistic(String userLogin) {
        return repository.findOneUserStatisticByLogin(userLogin);
    }

    public void delete(UserStatistic userStatistic) {
        repository.delete(userStatistic);
    }
}
