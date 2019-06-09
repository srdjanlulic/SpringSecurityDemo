package com.ftn.backend.services;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ftn.backend.security.JwtTokenProvider;

/**
 * Class which purpose is to accept token from front, parse the token and return user id.
 *
 */
@Service
public class TokenService {
	@Autowired
	JwtTokenProvider tokenProvider;
	
	
	/**
	 * Function for extracting user id from authorization header.
	 * @param authorizationHeader
	 * @return user id as Long
	 */
	public Long extractUserIdFromAuthorizationHeader(String authorizationHeader) {
		String token = authorizationHeader.split(" ")[1];
		return tokenProvider.getUserIdFromJWT(token);
	}
	
	/**
	 * Function for extracting user id from token
	 * @param token
	 * @return user id as Long
	 */
	public Long extractUserIdFromToken(String token) {
		return tokenProvider.getUserIdFromJWT(token);
	}
	
	/**
	 * Function which accesses and parses the Authorization header extracting the JWT token
	 * @param request
	 * @return JWT token as String
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
