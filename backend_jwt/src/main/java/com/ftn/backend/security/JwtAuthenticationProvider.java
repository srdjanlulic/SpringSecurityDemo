package com.ftn.backend.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * JWT authentication provider koji se koristi za manipulaciju <code>Authentication</code> objektom.
 * @author Srdjan Lulic
 *
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		if(authentication.getCredentials() != null) {
			return null;
		}
		
		authentication.getPrincipal();
		
		return null;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return false;
	}
	
}
