package com.ftn.backend.utils;

import java.nio.charset.Charset;

import org.apache.tomcat.util.codec.binary.Base64;

public class HttpUtils {

	/**
	 * Enkoduje username:password koji je prosledjen od strane korisnika i vraca hash vrednost koja se stavlja
	 * u authorization header pri basic autentifikaciji
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getBasicAuthToken(String username, String password) {
		String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64( 
           auth.getBytes(Charset.forName("US-ASCII")) );
        return new String( encodedAuth );
	}
}
