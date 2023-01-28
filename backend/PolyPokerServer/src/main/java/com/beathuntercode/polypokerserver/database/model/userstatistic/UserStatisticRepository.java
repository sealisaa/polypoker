package com.beathuntercode.polypokerserver.database.model.userstatistic;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserStatisticRepository extends CrudRepository<UserStatistic, String> {
    @Transactional
    @Modifying
    @Query("update UserStatistic u set u.totalGamesPlayed = ?2, u.winGames = ?3, u.currentCoinsCount = ?4, u.totalEarn = ?5 where u.login = ?1")
    void updateTotalGamesPlayedAndWinGamesAndCurrentCoinsCountAndTotalEarnBy(String login, int totalGamesPlayed, int winGames, int currentCoinsCount, int totalEarn);
    @Transactional
    @Modifying
    @Query("update UserStatistic u set u.currentCoinsCount = ?2 where u.login = ?1")
    void updateCurrentCoinsCountBy(String login, int currentCoinsCount);
    UserStatistic findOneUserStatisticByLogin(String login);
}
