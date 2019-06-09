package com.ftn.backend.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ftn.backend.model.User;
import com.ftn.backend.repositories.UserRepository;
import com.ftn.backend.services.CustomUserDetailsService;
import com.ftn.backend.services.TokenService;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TokenService tokenService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	/**
	 * Filter which validates the token and checks if it's in the blacklist. If the token is expired or in the blacklist, 
	 * a ResponseEntity with code GONE 410 is returned to the client. If the token isn't valid for any other reason or
	 * there is an authentication failure, the client will receive code UNAUTHORIZED 401.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
            String jwt = tokenService.getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            	
                Integer userId = tokenProvider.getUserIdFromJWT(jwt).intValue();
                User u = userRepo.findById(userId).get();
                
                CustomPrincipalUser userDetails = (CustomPrincipalUser) customUserDetailsService.loadUserByUsername(u.getUsername());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                		userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                
                
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch(ExpiredJwtException ex) {
        	response.setStatus(HttpStatus.GONE.value());
        	return;
        } catch (Exception ex) {
        	response.setStatus(HttpStatus.BAD_REQUEST.value());
        	ex.printStackTrace();
        }
        filterChain.doFilter(request, response);
		
	}
}
