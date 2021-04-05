package com.ada.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ada.dto.AccountDTO;
import com.ada.entity.Account;
import com.ada.entity.ForgotPassword;
import com.ada.entity.SendEmail;
import com.ada.response.Response;
import com.ada.service.AccountService;
import com.ada.service.ForgotPasswordService;
import com.ada.service.SendEmailService;
import com.ada.util.Bcrypt;

@RestController
@RequestMapping("account")
public class AccountController {

	@Autowired
	private AccountService service;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@GetMapping("/{account}")
	public ResponseEntity<Response<AccountDTO>> findById(@PathVariable("account") Long account){
		Response<AccountDTO> response = new Response<AccountDTO>();
		Optional<Account> a = service.findById(account); 
		
		if(!a.isPresent()) {
			response.getErrors().add("O usuario "+account+" não foi encontrado");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		response.setData(this.convertEntityToDto(a.get()));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PostMapping
	public ResponseEntity<Response<AccountDTO>> create(@Valid @RequestBody AccountDTO dto, BindingResult result){
		
		Response<AccountDTO> response = new Response<AccountDTO>();
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		Account account = service.save(this.convertDtoToEntity(dto));
		
		response.setData(this.convertEntityToDto(account));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{account}")
	public ResponseEntity<Response<String>> update(@PathVariable("account") Long account, @RequestBody AccountDTO dto, BindingResult result){
		Optional<Account> a = service.findById(account);
		
		if(!a.isPresent()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		
		Account ac = a.get();
		ac.setUserName(dto.getUserName());
		ac.setFirstName(dto.getFirstName());
		ac.setLastName(dto.getLastName());
		ac.setDocument(dto.getDocument());
		ac.setBirthday(dto.getBirthday());
		ac.setUpdatedAt(LocalDateTime.now());
		
		service.save(ac);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PatchMapping("/disable/{id}")
	public ResponseEntity<Response<String>> disable(@PathVariable("id") Long id){
		Optional<Account> a = service.findById(id);
		
		if(!a.isPresent()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		
		a.get().setActive(false);
		a.get().setUpdatedAt(LocalDateTime.now());
		
		service.save(a.get());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PostMapping("/forgot")
	public ResponseEntity<String> forgotPassword(@RequestBody AccountDTO  account, HttpServletRequest request){
		
		Optional<Account> a = service.findByEmail(account.getEmail());
		
		if(!a.isPresent()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
		
		forgotPasswordService.deleteByAccount(a.get().getId());
		
		String token = UUID.randomUUID().toString();
		
		ForgotPassword fp = new ForgotPassword();
		fp.setAccount(a.get());
		fp.setToken(token);
		fp.setCreatedAt(LocalDateTime.now());
		
		forgotPasswordService.save(fp);
		
		String appUrl = request.getScheme() + "://" + request.getServerName();
		
		SendEmail se = new SendEmail();
		se.setFrom("flaviojr.salviano@hotmail.com");
		se.setTo(a.get().getEmail());
		se.setSubject("Password Reset Request");
		se.setText("To reset your password, click the link below:\n" + appUrl +"/reset?token="+fp.getToken());
		se.setSent(false);
		
		sendEmailService.save(se);
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PostMapping("/reset")
	public ResponseEntity<Response<String>> resetPassword(@RequestParam Map<String, String> requestParams){
		Response<String> response = new Response<String>();
		
		if(requestParams.get("token") == null || requestParams.get("new_password") == null) {
			response.getErrors().add("Dados inválido");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		forgotPasswordService.deleteOldForgotPassword();
		
		Optional<ForgotPassword> fp = forgotPasswordService.findByToken(requestParams.get("token"));
		
		if(!fp.isPresent()) {
			response.getErrors().add("Token inválido");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		
		Optional<Account> a = service.findById(fp.get().getAccount().getId());
		
		a.get().setPassword(Bcrypt.getHash(requestParams.get("new_password")));
		a.get().setUpdatedAt(LocalDateTime.now());
		
		service.save(a.get());
		
		forgotPasswordService.deleteById(fp.get().getId());
		
		response.setData("Senha alterada com sucesso");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
	}
	
	private Account convertDtoToEntity(AccountDTO dto) {
		Account a = new Account();
		a.setId(dto.getId());
		a.setEmail(dto.getEmail());
		a.setUserName(dto.getUserName());
		a.setFirstName(dto.getFirstName());
		a.setLastName(dto.getLastName());
		a.setPassword(Bcrypt.getHash(dto.getPassword()));
		a.setDocument(dto.getDocument());
		a.setBirthday(dto.getBirthday());
		a.setType(dto.getType());
		a.setActive(dto.getActive());
		a.setCreatedAt(LocalDateTime.now());
		
		return a;
	}
	
	private AccountDTO convertEntityToDto(Account a) {
		AccountDTO dto = new AccountDTO();
		dto.setId(a.getId());
		dto.setEmail(a.getEmail());
		dto.setUserName(a.getUserName());
		dto.setFirstName(a.getFirstName());
		dto.setLastName(a.getLastName());
		dto.setPassword(a.getPassword());
		dto.setDocument(a.getDocument());
		dto.setBirthday(a.getBirthday());
		dto.setType(a.getType());
		dto.setActive(a.getActive());
		dto.setCreatedAt(LocalDateTime.now());
		
		return dto;
	}
	
    @ExceptionHandler({IOException.class})
    public ResponseEntity<Response<String>> handleAllExceptions() {
    	Response<String> response = new Response<String>();
		response.getErrors().add("Não foi possível processar esta solicitação");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
