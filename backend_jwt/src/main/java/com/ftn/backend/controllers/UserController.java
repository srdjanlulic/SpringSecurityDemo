package com.ftn.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.backend.model.User;
import com.ftn.backend.services.UserService;

@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	UserService userService; 
	
	@GetMapping
	public ResponseEntity<?> getUsers(){
		List<User> users = userService.listAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("register")
	public ResponseEntity<?> createUser(@RequestBody User user){
		user = userService.saveOrUpdate(user);
		
		return new ResponseEntity<>(user,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userID}")
	public ResponseEntity<?> deleteUser(@PathVariable("userID") Integer userID){
		userService.delete(userID);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
