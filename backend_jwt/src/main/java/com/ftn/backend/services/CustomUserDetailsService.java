package com.ftn.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.backend.model.User;
import com.ftn.backend.repositories.UserRepository;
import com.ftn.backend.security.CustomPrincipalUser;

@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
    private UserRepository userRepository;

	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
            throw new UsernameNotFoundException(
              "No user found with username: "+ username);
        }
		
		return new CustomPrincipalUser(user);
	}
}