package com.BankManagmentSystem.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BankManagmentSystem.model.Account;
import com.BankManagmentSystem.model.AccountStatus;
import com.BankManagmentSystem.model.AccountType;
import com.BankManagmentSystem.model.User;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findTopByOrderByIdDesc();

    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByCustomer_Email(String email);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByCustomerAndAccountType(User customer, AccountType accountType);

    List<Account> findByCustomer_Branch_BranchId(Long branchId);

    List<Account> findByCustomer_Branch_BranchIdAndStatus(
            Long branchId,
            AccountStatus status);

    boolean existsByCustomerAndAccountTypeAndStatus(
            User customer,
            AccountType accountType,
            AccountStatus status);
}
