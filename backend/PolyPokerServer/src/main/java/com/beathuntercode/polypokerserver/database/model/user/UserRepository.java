package com.beathuntercode.polypokerserver.database.model.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findOneUserByLogin(String login);
}
