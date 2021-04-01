package com.ada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	Optional<Account> findByEmailEquals(String email);
}
