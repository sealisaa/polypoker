package com.beathuntercode.polypokerserver.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

@Service
public class UserDao {

    @Autowired
    private UserRepository repository;

    public User save(User user) {
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        Streamable.of(repository.findAll()).forEach(usersList::add);
        return  usersList;
    }

    public User getUserByLogin(String login) {
        return repository.findById(login).get();
    }

    public void delete(User user) {
        repository.delete(user);
    }
}
