package com.ftn.backend.bootstrap;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ftn.backend.model.User;
import com.ftn.backend.services.UserService;

@Component
public class UserDatabaseBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	UserService userService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadUsers();
	}

	private void loadUsers() {
		if (userService.findByUsername("srdjanlulic") == null) {
			User user1 = new User();
			user1.setName("Srdjan");
			user1.setLastname("Lulic");
			user1.setEmail("lulic.ing@gmail.com");
			user1.setUsername("srdjanlulic");
			user1.setPassword("password");
			userService.saveOrUpdate(user1);

			User user2 = new User();
			user2.setName("Petar");
			user2.setLastname("Petrovic");
			user2.setEmail("petar.petrovic@gmail.com");
			user2.setUsername("pera.peric");
			user2.setPassword("password");
			userService.saveOrUpdate(user2);

		}
	}

}
