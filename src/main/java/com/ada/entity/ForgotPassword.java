package com.ada.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	
	@Column(nullable = true, name = "created_at")
	private LocalDateTime  createdAt;
}
