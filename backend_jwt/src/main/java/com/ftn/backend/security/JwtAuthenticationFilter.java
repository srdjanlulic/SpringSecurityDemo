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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ftn.backend.model.User;
import com.ftn.backend.repositories.UserRepository;
import com.ftn.backend.services.TokenService;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Filter klasa koja se primenjuje prilikom rukovanja korisnickim zahtevima. 
 * Implementira <code>OncePerRequestFilter</code> interfejs i preklapa metodu <code>doFilterInternal</code>
 * koja u ovom slucaju pre svakog korisnickog zahteva proverava validnost i postojanje korisnickog tokena.
 * @author Srdjan Lulic
 *
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private TokenService tokenService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	

	/**
	 * Filter koji validira token. Ukoliko je token istekao vraca se status kod 410 (GONE). Ukoliko token nije validan 
	 * iz bilo kog drugog razloga klijentu se kao odgovor vraca neautorizovani status kod 401 - UNAUTHORIZED.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
            String jwt = tokenService.getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            	
                Integer userId = tokenProvider.getUserIdFromJWT(jwt).intValue();
                User u = userRepo.findById(userId).get();
                
                CustomPrincipalUser userDetails = (CustomPrincipalUser) userDetailsService.loadUserByUsername(u.getUsername());
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
