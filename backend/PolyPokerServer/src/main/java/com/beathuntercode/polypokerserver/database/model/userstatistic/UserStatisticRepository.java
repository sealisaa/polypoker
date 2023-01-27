package com.beathuntercode.polypokerserver.database.model.userstatistic;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserStatisticRepository extends CrudRepository<UserStatistic, String> {
    @Transactional
    @Modifying
    @Query("update UserStatistic u set u.totalGamesPlayed = ?1, u.winGames = ?2, u.currentCoinsCount = ?3, u.totalEarn = ?4")
    void updateTotalGamesPlayedAndWinGamesAndCurrentCoinsCountAndTotalEarnBy(int totalGamesPlayed, int winGames, int currentCoinsCount, int totalEarn);
    @Transactional
    @Modifying
    @Query("update UserStatistic u set u.currentCoinsCount = ?1")
    void updateCurrentCoinsCountBy(int currentCoinsCount);
    UserStatistic findOneUserStatisticByLogin(String login);
}
