package com.ftn.backend.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.backend.dtos.AuthTokenDto;
import com.ftn.backend.dtos.LoginFormDTO;
import com.ftn.backend.model.User;
import com.ftn.backend.services.UserService;
import com.ftn.backend.utils.HttpUtils;

@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginFormDTO loginForm){

		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginForm.getUsername(), loginForm.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		//Izvlacenje informacija o korisniku koji pokusava da se uloguje
		UserDetails principal = (UserDetails)authentication.getPrincipal();
		AuthTokenDto authTokenDto = new AuthTokenDto(HttpUtils.getBasicAuthToken(loginForm.getUsername(), loginForm.getPassword()));
		return new ResponseEntity<>(authTokenDto, HttpStatus.OK);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> logout(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.setAuthenticated(false);
		
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
