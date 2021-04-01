package com.ada.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.entity.SendEmail;
import com.ada.repository.SendEmailRepository;
import com.ada.service.SendEmailService;

@Service
public class SendEmailServiceImpl implements SendEmailService{

	@Autowired
	SendEmailRepository repository;
	
	@Override
	public SendEmail save(SendEmail e) {
		return repository.save(e);
	}

}
