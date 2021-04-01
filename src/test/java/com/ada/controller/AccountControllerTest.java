package com.ada.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ada.dto.AccountDTO;
import com.ada.entity.Account;
import com.ada.service.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
	private static final Long   ID = 1L;
	private static final String USERNAME = "Account Test";
	private static final String EMAIL = "email@teste.com";
	private static final String PASSWORD = "q1w2e3";
	private static final String DOCUMENT = "123456789";
	private static final Date BIRTHDAY = new Date();
	private static final LocalDate   TODAY =  LocalDate.now();
	private static final String URL = "/account";
	
	@MockBean
	AccountService service;
	
	@Autowired
	MockMvc mvc;
	
	@Test
	public void testSave() throws JsonProcessingException, Exception {

		BDDMockito.given(service.save(Mockito.any(Account.class))).willReturn(getMockAccount());

		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, EMAIL, USERNAME, PASSWORD, DOCUMENT, BIRTHDAY))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.data.id").value(ID))
		.andExpect(jsonPath("$.data.userName").value(USERNAME))
		.andExpect(jsonPath("$.data.email").value(EMAIL))
		.andExpect(jsonPath("$.data.document").value(DOCUMENT))
		.andExpect(jsonPath("$.data.birthday").value(TODAY.format(getDateFormater())));
		
	}
	
	@Test
	public void testSaveInvalidAccount() throws JsonProcessingException, Exception {
		mvc.perform(MockMvcRequestBuilders.post(URL).content(getJsonPayload(ID, "email", USERNAME, PASSWORD, DOCUMENT, BIRTHDAY))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors[0]").value("Email inválido"));
	}

	public Account getMockAccount() {
		Account a = new Account();
		a.setId(ID);
		a.setUserName(USERNAME);
		a.setEmail(EMAIL);
		a.setPassword(PASSWORD);
		a.setDocument(DOCUMENT);
		a.setBirthday(BIRTHDAY);
		
		return a;
	}
	
	public String getJsonPayload(Long id, String email, String userName, String password, String document, Date birthday) throws JsonProcessingException {
		AccountDTO dto = new AccountDTO();
		dto.setId(id);
		dto.setUserName(userName);
		dto.setEmail(email);
		dto.setPassword(password);
		dto.setDocument(document);
		dto.setBirthday(birthday);
		
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(dto);
	}
	
	private DateTimeFormatter getDateFormater() {
		return DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
}
