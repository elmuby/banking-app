package com.mydomain.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mydomain.banking.entity.Account;

public interface AccountsRepository extends JpaRepository<Account, Long> {

}
