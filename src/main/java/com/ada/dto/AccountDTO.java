package com.ada.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class AccountDTO {
	
	private Long id;
	
	@NotNull
	@Length(min=6, max=100, message="O nome de usuário deve conter entre 6 e 100 caracteres")
	private String userName;
	
	@NotNull
	@Email(message="Email inválido")
	private String email;
	
	@NotNull
	@Length(min=6, message="A senha deve conter no mínimo 6 caracteres")
	private String password;
	
	@Length(min=2, max=100, message="O nome deve conter entre 2 e 100 caracteres")
	private String firstName;
	
	@Length(min=2, max=200, message="O nome deve conter entre 2 e 200 caracteres")
	private String lastName;
	
	@Length(min=3, max=30,message="O documento deve conter entre 3 e 30 caracteres")
	private String document;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "pt_BR", timezone = "Brazil/East")
	private Date birthday;
	
	private Integer type;
	
	private Boolean active;
}
