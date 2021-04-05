package com.ada.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.ada.entity.ForgotPassword;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Long>{
	Optional<ForgotPassword> findByTokenEquals(String token);

	@Transactional
	@Modifying
	@Query(value = "delete from ForgotPassword f where f.account.id = :account")
	void deleteByAccount(@Param("account") Long account);
	
	@Transactional
	@Modifying
	@Query(value = "delete from forgot_password f where f.created_at < (CURRENT_TIMESTAMP - INTERVAL '5 MINUTES')", nativeQuery = true)
	void deleteOldForgotPassword();
}
