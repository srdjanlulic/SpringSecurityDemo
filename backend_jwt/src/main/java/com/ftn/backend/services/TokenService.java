package com.ftn.backend.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ftn.backend.security.JwtTokenProvider;

/**
 * Klasa koja ima svrhu prihvatanja korisnickog tokena i parsiranja korisnickih informacija iz njega
 * 
 * @author Srdjan Lulic
 *
 */
@Service
public class TokenService {
	
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	
	/**
	 * Funkcija za ekstrakciju korisnickog id-a iz Authorization header-a
	 * @param authorizationHeader
	 * @return user id kao Long
	 */
	public Long extractUserIdFromAuthorizationHeader(String authorizationHeader) {
		String token = authorizationHeader.split(" ")[1];
		return tokenProvider.getUserIdFromJWT(token);
	}
	
	/**
	 * Funkcija za ekstrakciju korisnickog ID-a iz tokena
	 * @param token
	 * @return user id kao Long
	 */
	public Long extractUserIdFromToken(String token) {
		return tokenProvider.getUserIdFromJWT(token);
	}
	
	/**
	 * 
	 * Funkcija koja pristupa korisnickom zahtevu i parsira Authorization header kako bi izvukla JWT.
	 * @param request
	 * @return JWT token kao String
	 */
	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		try {
		    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
		    	return bearerToken.substring(7, bearerToken.length());
		    }
		} catch(IllegalArgumentException exc) {
			return null;
		}
	    
	    return null;  
	}
	
}
