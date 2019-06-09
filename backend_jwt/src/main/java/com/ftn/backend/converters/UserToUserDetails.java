package com.ftn.backend.converters;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ftn.backend.model.User;
import com.ftn.backend.services.UserDetailsImpl;

/**
 * Spring komponenta koja se koristi za konverziju User objekata u UserDetails objekte implementacijom 
 * Converter interfejsa.
 * 
 * @author Srdjan Lulic
 *
 */

@Component("userUserDetailsConverter")
public class UserToUserDetails implements Converter<User, UserDetails>{
	
	/**
	 * Konverzija modelske klase User u UserDetails sto je neophodno za authentication providera. 
	 * @param user - objekat klase Korisnik
	 * 
	 * @return user details - konvertovani korisnik za upotrebu u okviru spring security-ja
	 */
	@Override
	public UserDetails convert(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        
        if (user != null) {
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getEncryptedPassword());
            
            /*Da li je korisnik "enabled" se ne spominje u specifikaciji projektnog zadatka - stoga uvek true*/
            userDetails.setEnabled(true); 
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            
            /*Rola "ROLE_USER" je zakucana jer nisu definisane role*/
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            userDetails.setAuthorities(authorities);
        }
 
        return userDetails;
	}

}
