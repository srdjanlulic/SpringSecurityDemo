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
 * Svha ove klase je da enkapsulira sve akcije vezane za JSON Web Token, koje podrazumevaju generisanje tokena sa 
 * raznim parametrima, validaciju, ekstrakciju parametara iz tokena i sl.
 * @author Srdjan Lulic
 */
@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Value("${app.jwtSecret}")
    private String jwtSecret;
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    
    /**
     * Funkcija za generisanje JWT tokena koriscenjem user details i setovanje vremena izdavanja i vremena isticanja tokena
     * na osnovu propertija setovanog u statickim resursima. Koristi se hs512 signature algoritam
     * @param authentication - UserNamePasswordAuthenticationToken objekat koji sadrzi korisnicke kredencijale
     * @return JWT token kao string
     */
    public String generateToken(Authentication authentication) {
    	
    	CustomPrincipalUser user = (CustomPrincipalUser)authentication.getPrincipal();
    	
    	Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        
        return Jwts.builder()
        		/*Enkodovanje ID-a kao najznacajnijeg atributa u JWT*/
                .setSubject(Long.toString(user.getUser().getId()))
                
                /*Razliciti custom claims-i mogu da se enkoduju u token i cupaju pri svakom requestu*/
                .claim("ROLE", "ROLE_USER")
                
                /*Setovanje vremena izdavanja i isticanja*/
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
        
    }
    
    /**
     * Funkcija za ekstrakciju korisnickog ID-a iz tokena.
     * @param token
     * @return user id kao Long
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    
    /**
     * Funkcija za ekstrakciju role iz tokena (nepotrebno sada ali zanimljiva tema za prosirenje)
     * @param token
     * @return role name kao string (trenutno podrzava ekstrakciju samo jedne role)
     */
    public Long getRoleFromJWT(String token) {
    	Claims claims = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token)
    			.getBody();
    	
    	return Integer.toUnsignedLong((int) claims.get("ROLE"));
    }
    
    /**
     * Funkcija za ekstrakciju roka vazenja iz JWT tokena
     * @param token
     * @return rok upotrebe tokena kao datum
     */
    public Date getExpirationDateFromJWT(String token) {
    	Claims claims = Jwts.parser()
    			.setSigningKey(jwtSecret)
    			.parseClaimsJws(token)
    			.getBody();
    	
    	return claims.getExpiration();
    }
    
    /**
     * Funkcija za validaciju tokena upotrebom kljuca
     * @param token
     * @return true ako je token validan, false ako nije
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