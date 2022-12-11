package com.beathuntercode.polypokerserver;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.beathuntercode.polypokerserver.model.User;
import com.beathuntercode.polypokerserver.model.UserDao;

@SpringBootTest
class PolyPokerServerApplicationTests {

	@Autowired
	private UserDao userDao;

	@Test
	void addUserTest() {
		User user = new User();
		user.setName("Nikaa");
		user.setSurname("Sheraa");
		user.setLogin("bhaa");
		user.setPassword("12345");
		userDao.save(user);
	}

	@Test
	void getAllUsers() {
		List<User> usersList = userDao.getAllUsers();
		System.out.println(usersList);
	}

	@Test
	void deleteAllUsers() {
		List<User> usersList = userDao.getAllUsers();
		for (User user: usersList) {
			userDao.delete(user);
		}
	}

}
