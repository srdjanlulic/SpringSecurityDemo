package com.ftn.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object koji se koristi kako bi se klijent mogao identifikovati. 
 * Bilo u slucaju JWT ili basic autentifikacije ovaj token se smesta u local storage u browser-u 
 * (nije idealno, bolje u cookie - za prodiskutovati) i potom se pri svakom klijentskom zahtevu ubacuje vrednost 
 * ovog tokena u Authorization header. 
 * - u slucaju JWT header je Authorization=Bearer {JWT}
 * - u slucaju Basic header je Authorization=Basic {Base64 kod}
 * 
 * @author Srdjan Lulic
 *
 */
@Data
@AllArgsConstructor
public class AuthTokenDto {
	private String token;
}
