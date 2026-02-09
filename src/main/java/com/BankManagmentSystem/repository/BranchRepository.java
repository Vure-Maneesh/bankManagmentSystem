package com.BankManagmentSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankManagmentSystem.model.Bank;
import com.BankManagmentSystem.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    // get all branches of a bank
    List<Branch> findByBank(Bank bank);

    // check duplicate branch name within the same bank
    boolean existsByNameAndBank(String name, Bank bank);

    // get branches by city (optional feature)
    List<Branch> findByCity(String city);

}
