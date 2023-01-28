package com.beathuntercode.polypokerserver.database.model.userstatistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateUserStatistic(String login, int totalGamesPlayed, int winGames, int currentCoinsCount, int totalEarn) {
        repository.updateTotalGamesPlayedAndWinGamesAndCurrentCoinsCountAndTotalEarnBy(
                login,
                totalGamesPlayed,
                winGames,
                currentCoinsCount,
                totalEarn
        );
    }

    public void updateUserCurrentCoinsCount(String login, int currentCoinsCount) {
        repository.updateCurrentCoinsCountBy(login, currentCoinsCount);
    }
}
