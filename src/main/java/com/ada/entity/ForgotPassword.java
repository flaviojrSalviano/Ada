package com.ada.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "forgot_password")
public class ForgotPassword implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7437978615377113754L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@JoinColumn(name="account", referencedColumnName = "id")
	@OneToOne(fetch = FetchType.LAZY)
	private Account account;
	
	@NotNull
	private String token;
}
