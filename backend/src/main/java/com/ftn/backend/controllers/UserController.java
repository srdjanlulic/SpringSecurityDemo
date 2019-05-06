package com.ftn.backend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.backend.model.User;

@RestController
public class UserController {
	
	@GetMapping("users")
	public ResponseEntity<?> getUsers(){
		User user = new User();
		user.setName("Srdjan");
		user.setLastname("Lulic");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
