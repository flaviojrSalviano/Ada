package com.ada.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ada.entity.SendEmail;

public interface SendEmailRepository extends JpaRepository<SendEmail, Long>{

}
