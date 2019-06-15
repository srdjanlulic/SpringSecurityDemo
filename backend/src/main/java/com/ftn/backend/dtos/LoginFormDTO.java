package com.ftn.backend.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

/**
 * Login Data Transfer Object koji se koristi za prosledjivanje korisnickog imena i lozinke
 * prilikom autentifikacije
 * @author Srdjan Lulic
 *
 */
@Data /*Lombok anotacija*/
public class LoginFormDTO {
	
	@NotNull
	private String username;
	
	@NotNull
	@Size(min=8)
	private String password;
}
