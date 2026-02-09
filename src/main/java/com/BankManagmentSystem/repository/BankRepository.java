package com.BankManagmentSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankManagmentSystem.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
    boolean existsByName(String name);

    long count();

}
