package com.ada.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ForgotPasswordDTO {

	@NotNull
	private Long id;
	
	@NotNull
	private Long account;
	
	@NotNull
	private String token;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss[.SSS][.SS][.S]")
	private LocalDateTime  createdAt;
}
