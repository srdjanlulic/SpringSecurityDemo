package com.ftn.backend.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginFormDTO {
	
	@NotNull
	private String username;
	
	@NotNull
	@Size(min=8)
	private String password;
}
