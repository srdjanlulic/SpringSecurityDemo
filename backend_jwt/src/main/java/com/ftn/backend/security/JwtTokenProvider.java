package com.ftn.backend.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * The purpose of this class is to encapsulate all actions concerning the Json Web Token which are generating the token, 
 * exctracting necessary data from the token, as well as validating the token using the signing key. 
 * @author Nikola Skrobonja
 *
 */
@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    
    /**
     * Function for generating JWT token using user details and setting issued date and expiration date(expiration time 
     * defined in static resources). Signature algorithm used is hs512.
     * @param authentication UsernamePasswordAuthenticationToken object which holds user credentials.
     * @return JWT token as String
     */
    public String generateToken(Authentication authentication) {
    	
    	CustomPrincipalUser principal = (CustomPrincipalUser)authentication.getPrincipal();
    	
    	Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        return Jwts.builder()
                .setSubject(Long.toString(principal.getUser().getId()))
                .claim("ROLE", 1)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        
    }
    
    /**
     * Function for extracting user id from token
     * @param token
     * @return user id as Long
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    
    /**
     * Function for exctracting role from JWT token
     * @param token
     * @return role name as a String
     */
    public Long getRoleFromJWT(String token) {
    	Claims claims = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token)
    			.getBody();
    	
    	return Integer.toUnsignedLong((int) claims.get("ROLE"));
    }
    
    /**
     * Function for exctracting expiration date from JWT token
     * @param token
     * @return expiration date as Date
     */
    public Date getExpirationDateFromJWT(String token) {
    	Claims claims = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token)
    			.getBody();
    	
    	return claims.getExpiration();
    }
    
    /**
     * Function for validating token using signing key
     * @param authToken token value to be validated
     * @return true if valid otherwise false
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        }  catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
	
}