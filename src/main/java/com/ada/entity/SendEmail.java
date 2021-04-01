package com.ada.entity;

import java.io.Serializable;
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
@Table(name = "send_email")
public class SendEmail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7437978615377113754L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Column(name = "from_email")
	private String from;
	
	@NotNull
	@Column(name = "to_email")
	private String to;

	@NotNull
	private String subject;
	
	@NotNull
	private String text;
	
	@NotNull
	private Boolean sent;
}
