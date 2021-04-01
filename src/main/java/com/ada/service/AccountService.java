package com.ada.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.ada.entity.Account;

public interface AccountService {
	Account save(Account a);
	
	Optional<Account> findByEmail(String email);
	
	Optional<Account> findById(Long id);
	
	Page<Account> findAll(int page);
	
	void deleteById(Long id);
}
