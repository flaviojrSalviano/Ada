package com.ada.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ForgotPasswordDTO {

	@NotNull
	private Long id;
	
	@NotNull
	private Long account;
	
	@NotNull
	private String token;
}
