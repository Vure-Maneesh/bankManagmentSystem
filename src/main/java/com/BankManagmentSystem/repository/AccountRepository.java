package com.BankManagmentSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankManagmentSystem.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(Long accountNumber);

    List<Account> findByCustomerEmail(String email);

    Optional<Account> findByAccountNumber(Long accountNumber);

}
