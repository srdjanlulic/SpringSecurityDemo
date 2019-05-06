package com.ftn.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ftn.backend.model.User;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private Converter<User, UserDetails> userUserDetailsConverter;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userUserDetailsConverter.convert(userService.findByUsername(username));
	}

}
