package com.BankManagmentSystem.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.BankManagmentSystem.repository.AccountRepository;

@Component
public class AccountNumberGenerator {

    @Autowired
    private AccountRepository accountRepository;

    public Long generateAccountNumber() {

        Long accountNumber;

        do {
            accountNumber = (long) (Math.random() * 1_0000_0000_00L);
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

}
