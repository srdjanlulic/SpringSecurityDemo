package com.ftn.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.backend.dtos.LoginFormDTO;

@RestController
@RequestMapping("api/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginFormDTO loginForm){

		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginForm.getUsername(), loginForm.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> logout(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.setAuthenticated(false);
		
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
