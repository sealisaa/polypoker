package com.beathuntercode.polypokerserver.model.userstatistic;

import org.springframework.data.repository.CrudRepository;

public interface UserStatisticRepository extends CrudRepository<UserStatistic, String> {
    UserStatistic findOneUserStatisticByLogin(String login);
}
