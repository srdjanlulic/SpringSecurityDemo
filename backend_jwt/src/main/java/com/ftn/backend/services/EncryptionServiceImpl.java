package com.ftn.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementacija <code>EncryptionService</code> interfejsa koja za enkripciju i proveravanje lozinki 
 * koristi <code>BCryptPasswordEncoder</code>.
 * @author Srdjan Lulic
 *
 */
@Service("EncryptionService")
public class EncryptionServiceImpl implements EncryptionService{
	
	/**
	 * Password encoder iz <code>CommonBeanConfig</code>-a.
	 * 
	 */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * Enkriptuje prosledjeni string.
	 * 
	 * @param ulazni string
	 * @return enkriptovani string
	 */
	@Override
	public String encryptString(String input) {
		return bCryptPasswordEncoder.encode(input);		
	}

	/**
	 * Uporedjuje nekriptovanu i kriptovanu lozinku
	 * @param nekriptovana lozinka 
	 * @param kriptovana lozinka
	 */
	@Override
	public boolean checkPassword(String plainPassword, String encryptedPassword) {
		return bCryptPasswordEncoder.matches(plainPassword, encryptedPassword);
	}

}
