package com.ftn.backend.services;

public interface EncryptionService {
	String encryptString(String input);
    boolean checkPassword(String plainPassword, String encryptedPassword);
}
