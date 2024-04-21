package com.mydomain.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydomain.banking.dto.AccountDto;
import com.mydomain.banking.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	private AccountService accountService;
	
	public AccountController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}
	
//	Add account REST API
	
	@PostMapping
	public ResponseEntity<AccountDto> addAccount (@RequestBody AccountDto accountDto){
		return new ResponseEntity<AccountDto>(accountService.createAccount(accountDto), HttpStatus.CREATED); 
	}
	
//	Get Account REST API
	
	@GetMapping("/{id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
		AccountDto accountDto = accountService.getAccountById(id);
		return ResponseEntity.ok(accountDto);
	}
	
//	deposit account REST API
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto> deposit (@PathVariable Long id,@RequestBody Map<String, Double> request){
		Double amount = request.get("amount");
		AccountDto accountDto = accountService.deposit(id, amount);
		return ResponseEntity.ok(accountDto);
		
	}
	
//	withdraw REST API
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto> withdraw (@PathVariable Long id,@RequestBody Map<String, Double> request){
		double amount = request.get("amount");
		AccountDto accountDto = accountService.withdraw(id, amount);
		return ResponseEntity.ok(accountDto);
	}
	
//	retrieve all accounts REST API
	@GetMapping
	public ResponseEntity<List<AccountDto> > getAllAcounts(){
		return ResponseEntity.ok(accountService.getAllAccount());
	
	}
	
//	Delete account REST API
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		return ResponseEntity.ok("Account deleted successfully");
	}

}
