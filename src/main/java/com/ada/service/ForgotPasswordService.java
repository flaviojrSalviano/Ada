package com.ada.service;

import java.util.Optional;

import com.ada.entity.ForgotPassword;

public interface ForgotPasswordService {
	ForgotPassword save(ForgotPassword f);
	
	Optional<ForgotPassword> findByToken(String token);
	
	void deleteById(Long id);
	
	void deleteByAccount(Long id);
	
	void deleteOldForgotPassword();
}
