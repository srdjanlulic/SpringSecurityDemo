package com.ftn.backend.services;

/**
 * Enkripcioni servisni interfejs
 * @author Srdjan Lulic
 *
 */
public interface EncryptionService {
	
	String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}
