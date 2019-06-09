package com.ftn.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service("EncryptionService")
public class EncryptionServiceImpl implements EncryptionService{
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public String encryptString(String input) {
		return bCryptPasswordEncoder.encode(input);		
	}

	@Override
	public boolean checkPassword(String plainPassword, String encryptedPassword) {
		return bCryptPasswordEncoder.matches(plainPassword, encryptedPassword);
	}

}
