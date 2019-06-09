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
import com.ftn.backend.security.JwtTokenProvider;
import com.ftn.backend.services.TokenService;
import com.ftn.backend.services.UserService;
import com.ftn.backend.utils.HttpUtils;

/**
 * REST kontroler za upravljanje autentifikcionim endpoint-ovima. 
 * @author Srdjan Lulic
 *
 */
@RestController
@CrossOrigin
@RequestMapping("api/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	UserService userService;
	
    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private TokenService tokenService;
	
    /**
     * Endpoint za login
     * @param loginForm (JSON objekat sa username i password vrednostima)
     * @return generisani JWT token
     */
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginFormDTO loginForm){

		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginForm.getUsername(), loginForm.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = tokenProvider.generateToken(authentication);
		AuthTokenDto authTokenDto = new AuthTokenDto(jwt);
		return new ResponseEntity<>(authTokenDto, HttpStatus.OK);
	}
	
	/**
	 * Logout opcija za JWT nema smisla osim u slucaju uvodjenja kesiranja tokena koji su odjavljeni i proveravanje istih
	 * prilikom svakog zahteva.
	 * @return Status 200*/
	@GetMapping("/logout")
	public ResponseEntity<?> logout(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		authentication.setAuthenticated(false);
		
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
