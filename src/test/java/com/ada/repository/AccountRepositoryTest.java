package com.ada.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ada.entity.Account;

@SpringBootTest
public class AccountRepositoryTest {
	private static final String USERNAME = "Account Test";
	private static final String EMAIL = "email@teste.com";
	private static final String PASSWORD = "123456";
	private static final String DOCUMENT = "123456789";
	private static final Integer TYPE = 0;
	private static final Date BIRTHDAY = new Date();
	
	private Long accountIdSaved = null;
	
	@Autowired
	AccountRepository repository;
	
	@BeforeEach
	public void setUp() {
		Account a = new Account();
		a.setUserName("Set user_name account");
		a.setEmail(EMAIL);
		a.setPassword("123456");
		a.setDocument("1234567");
		a.setBirthday(new Date());
		a.setType(0);
		
		repository.save(a);
		
		accountIdSaved = a.getId();
	}
	
	@AfterEach
	public void tearDown() {
		repository.deleteAll();
	}
	
	@Test
	public void testSave() {
		Account a = new Account();
		a.setUserName("Teste");
		a.setPassword("123456");
		a.setEmail("teste@teste.com");
		a.setDocument("1234567");
		a.setBirthday(new Date());
		a.setType(1);
		
		Account response = repository.save(a);
		
		assertNotNull(response);
	}
	
	@Test
	public void testSaveInvalidAccount() {
		  Account a = new Account();
			a.setUserName("Account name");
			a.setPassword(null);
			a.setEmail("account@email.com");
			a.setDocument(null);
			a.setBirthday(null);
			
	        assertThrows(ConstraintViolationException.class, () -> {
	        	Account response = repository.save(a);
	        });
	}
	
	@Test
	public void testUpdate() {
		Optional<Account> a = repository.findById(accountIdSaved);
		Account changed = a.get();
		
		String doc = "Novo doc";
		changed.setDocument(doc);
		
		repository.save(changed);
		
		Optional<Account> newA = repository.findById(accountIdSaved);
		assertEquals(doc, newA.get().getDocument());
	}
	
	@Test
	public void testDelete() {
		Account a = new Account();
		a.setUserName(USERNAME);
		a.setEmail(EMAIL);
		a.setPassword(PASSWORD);
		a.setDocument(DOCUMENT);
		a.setBirthday(BIRTHDAY);
		a.setType(TYPE);
		
		repository.save(a);
		
		repository.deleteById(a.getId());
		Optional<Account> response = repository.findById(a.getId());
		assertFalse(response.isPresent());
	}
	
	@Test
	public void testFindByEmail() {
		Optional<Account> response = repository.findByEmailEquals(EMAIL);
		assertTrue(response.isPresent());
		assertEquals(response.get().getEmail(), EMAIL);
	}
}
