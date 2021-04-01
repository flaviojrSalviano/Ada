package com.ada.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ada.entity.Account;
import com.ada.repository.AccountRepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

@SpringBootTest
public class AccountServiceTest {
	@MockBean
	AccountRepository repository;
	
	@Autowired
	AccountService service;
	
	@BeforeEach
	public void setUp() {
		BDDMockito.given(repository.findByEmailEquals(Mockito.anyString())).willReturn(Optional.of(new Account()));
		
	}
	
	@Test
	public void testFindByEmail() {
		Optional<Account> account = service.findByEmail("email@test.com");
		assertTrue(account.isPresent());
	}
}
