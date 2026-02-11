package com.BankManagmentSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.interfaces.ManagerService;
import com.BankManagmentSystem.model.Account;
import com.BankManagmentSystem.model.AccountStatus;
import com.BankManagmentSystem.model.User;
import com.BankManagmentSystem.repository.AccountRepository;
import com.BankManagmentSystem.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public ManagerServiceImpl(AccountRepository accountRepository,
            UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void approveAccount(Long accountId) {

        User manager = getLoggedInUser();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Business rule: Only PENDING accounts
        if (account.getStatus() != AccountStatus.PENDING) {
            throw new RuntimeException("Only PENDING accounts can be approved");
        }

        // Business rule: Same branch validation
        if (!manager.getBranch().getBranchId()
                .equals(account.getCustomer().getBranch().getBranchId())) {

            throw new RuntimeException(
                    "You can approve only accounts from your branch");
        }

        // Generate unique 11-digit account number
        String accountNumber = generateAccountNumber();

        account.setAccountNumber(accountNumber);
        account.setStatus(AccountStatus.ACTIVE);

        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void rejectAccount(Long accountId) {

        User manager = getLoggedInUser();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Business rule: Only PENDING accounts
        if (account.getStatus() != AccountStatus.PENDING) {
            throw new RuntimeException("Only PENDING accounts can be rejected");
        }

        // Business rule: Same branch validation
        if (!manager.getBranch().getBranchId()
                .equals(account.getCustomer().getBranch().getBranchId())) {

            throw new RuntimeException(
                    "You can reject only accounts from your branch");
        }

        account.setStatus(AccountStatus.REJECTED);

        accountRepository.save(account);
    }

    private User getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String generateAccountNumber() {

        for (int i = 0; i < 5; i++) {

            long number = 10000000000L +
                    (long) (Math.random() * 90000000000L);

            String accountNumber = String.valueOf(number);

            if (!accountRepository.existsByAccountNumber(accountNumber)) {
                return accountNumber;
            }
        }

        throw new RuntimeException("Unable to generate unique account number");
    }

    @Override
    public List<Account> getAllAccounts() {

        return accountRepository.findAll();

    }
}
