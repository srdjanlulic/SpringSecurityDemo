package com.ftn.backend.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.management.relation.Role;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ftn.backend.model.User;
import com.google.common.base.Optional;

public class CustomPrincipalUser implements UserDetails{
	private User user;
	

	public CustomPrincipalUser(User user) {
		super();
		this.user = user;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		 List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		 authorities.add(new SimpleGrantedAuthority("USER"));

	     return authorities;
	}
	@Override
	public String getPassword() {
		return user.getPassword().toString();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	@Override
	public String getUsername() {
		return user.getUsername();
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}