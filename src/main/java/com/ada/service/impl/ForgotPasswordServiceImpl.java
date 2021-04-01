package com.ada.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ada.entity.Account;
import com.ada.entity.ForgotPassword;
import com.ada.repository.ForgotPasswordRepository;
import com.ada.service.ForgotPasswordService;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService{

	@Autowired
	ForgotPasswordRepository repository;
	
	@Override
	public ForgotPassword save(ForgotPassword f) {
		return repository.save(f);
	}

	@Override
	public Optional<ForgotPassword> findByToken(String token) {
		return repository.findByTokenEquals(token);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public void deleteByAccount(Long id) {
		repository.deleteByAccount(id);
	}

}
