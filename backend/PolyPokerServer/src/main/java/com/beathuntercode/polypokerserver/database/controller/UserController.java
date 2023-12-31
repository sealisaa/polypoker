package com.beathuntercode.polypokerserver.database.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.beathuntercode.polypokerserver.database.model.user.User;
import com.beathuntercode.polypokerserver.database.model.user.UserDao;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/user/get-all")
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @GetMapping("/user/{login}")
    public User getUserByLogin(@PathVariable String login) {
        return userDao.getUserByLogin(login);
    }
    
    @PostMapping("/user/save")
    public User saveUser(@RequestBody User user) {
        return userDao.save(user);
    }

    @PostMapping("/user/auth")
    public boolean authorizeUser(@RequestBody User user) { return userDao.isUserAuthorized(user); }
}
