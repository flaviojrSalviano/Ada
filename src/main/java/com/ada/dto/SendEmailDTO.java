package com.ada.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class SendEmailDTO {

	private Long id;
	
	@NotNull
	private String from;
	
	@NotNull
	private String to;
	
	@NotNull
	private String subject;
	
	@NotNull
	private String text;
	
	@NotNull
	private Boolean sent;
}
