package com.ada.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "account")
public class Account implements Serializable{

	private static final long serialVersionUID = -7437978615377113754L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "user_name")
	private String userName;
	
	private String email;
	
	private String password;
	
	@Column(nullable = true, name = "first_name")
	private String firstName;
	
	@Column(nullable = true, name = "last_name")
	private String lastName;
	
	@Column(nullable = true)
	private String document;
	
	@Column(nullable = true)
	private Date birthday;
	
	private Integer type;
	
	private Boolean active;
	
	@Column(nullable = true, name = "created_at")
	private LocalDateTime  createdAt;
	
	@Column(nullable = true, name = "updated_at")
	private LocalDateTime  updatedAt;
}
