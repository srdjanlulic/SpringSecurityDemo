package com.ftn.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Konfiguraciona klasa koja sadrzi sve globalne bean-ove.
 * @author Srdjan Lulic
 *
 */
@Configuration
public class CommonBeanConfig {
	
	/**
	 * BCryptPasswordEncoder bean za enkodovanje/dekodovanje lozinki sacuvanih u bazi
	 * @return
	 */
	@Bean(name="bCryptPasswordEncoder")
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		return bCryptPasswordEncoder;
	}
}
