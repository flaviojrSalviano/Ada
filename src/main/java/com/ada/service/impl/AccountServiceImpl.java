package com.ada.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ada.entity.Account;
import com.ada.service.AccountService;
import com.ada.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountRepository repository;
	
	@Override
	public Account save(Account a) {
		return repository.save(a);
	}

	@Override
	public Optional<Account> findByEmail(String email) {
		return repository.findByEmailEquals(email);
	}

	@Override
	public Optional<Account> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public Page<Account> findAll(int page) {
		int playersPerPage = 2;
        PageRequest pageRequest = PageRequest.of(
                page,
                playersPerPage,
                Sort.Direction.ASC,
                "id");
        return repository.findAll(pageRequest);
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
