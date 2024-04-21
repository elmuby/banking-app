package com.mydomain.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mydomain.banking.dto.AccountDto;
import com.mydomain.banking.entity.Account;
import com.mydomain.banking.mapper.AccountMapper;
import com.mydomain.banking.repository.AccountsRepository;
import com.mydomain.banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountsRepository accountRespository;

	public AccountServiceImpl(AccountsRepository accountRespository) {
		super();
		this.accountRespository = accountRespository;
	}

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRespository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRespository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist"));

		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountRespository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist"));
		double newBalance = account.getBalance() + amount;
		account.setBalance(newBalance);
		Account savedAccount = accountRespository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account = accountRespository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist"));

		if (account.getBalance() < amount) {
			throw new RuntimeException("insufficient fund");
		}
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account savedAccount = accountRespository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public List<AccountDto> getAllAccount() {
		List<Account> accounts = accountRespository.findAll();
		return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());

	}

	@Override
	public void deleteAccount(Long id) {
		Account account = accountRespository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exist"));
		accountRespository.deleteById(account.getId());

	}

}
