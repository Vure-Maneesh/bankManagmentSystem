package com.BankManagmentSystem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.dtos.AccountApprovalResponseDTO;
import com.BankManagmentSystem.dtos.AccountResponseDTO;
import com.BankManagmentSystem.interfaces.EmailService;
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
    private final EmailService emailService;
    private final ModelMapper mapper; // ✅ FIX

    public ManagerServiceImpl(AccountRepository accountRepository,
            UserRepository userRepository,
            EmailService emailService,
            ModelMapper mapper) { // ✅ FIX
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.mapper = mapper; // ✅ FIX
    }

    /* ================= APPROVE ================= */

    @Override
    @Transactional
    public AccountApprovalResponseDTO approveAccount(Long accountId) {

        User manager = getLoggedInUser();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getStatus() != AccountStatus.PENDING) {
            throw new RuntimeException("Only pending accounts can be approved");
        }

        // 🔥 Branch validation
        if (!manager.getBranch().getBranchId()
                .equals(account.getCustomer().getBranch().getBranchId())) {

            throw new RuntimeException(
                    "You can approve only accounts from your branch");
        }

        String accountNumber = generateAccountNumber();

        account.setAccountNumber(accountNumber);
        account.setStatus(AccountStatus.ACTIVE);

        accountRepository.save(account);

        AccountApprovalResponseDTO response = new AccountApprovalResponseDTO();
        response.setMessage("Account approved successfully");
        response.setAccountNumber(Long.parseLong(accountNumber));
        response.setCustomerEmail(account.getCustomer().getEmail());

        return response;
    }

    /* ================= REJECT ================= */

    @Override
    @Transactional
    public void rejectAccount(Long accountId) {

        User manager = getLoggedInUser();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getStatus() != AccountStatus.PENDING) {
            throw new RuntimeException("Only PENDING accounts can be rejected");
        }

        if (!manager.getBranch().getBranchId()
                .equals(account.getCustomer().getBranch().getBranchId())) {

            throw new RuntimeException(
                    "You can reject only accounts from your branch");
        }

        account.setStatus(AccountStatus.REJECTED);
        accountRepository.save(account);
    }

    /* ================= GET ACCOUNTS BY BRANCH ================= */

    @Override
    public List<AccountResponseDTO> getAccountsByBranch() {

        User manager = getLoggedInUser();

        if (manager.getBranch() == null) {
            throw new RuntimeException("Manager not assigned to branch");
        }

        List<Account> accounts = accountRepository
                .findByCustomer_Branch_BranchIdAndStatus(
                        manager.getBranch().getBranchId(),
                        AccountStatus.PENDING); // 🔥 Backend filtering

        return accounts.stream()
                .map(account -> mapper.map(account, AccountResponseDTO.class))
                .collect(Collectors.toList());
    }

    /* ================= GET ALL ================= */

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /* ================= HELPER METHODS ================= */

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
    public List<AccountResponseDTO> getAllAccountsByBranch() {

        User manager = getLoggedInUser();

        if (manager.getBranch() == null) {
            throw new RuntimeException("Manager not assigned to branch");
        }

        List<Account> accounts = accountRepository
                .findByCustomer_Branch_BranchId(
                        manager.getBranch().getBranchId());

        return accounts.stream()
                .map(account -> mapper.map(account, AccountResponseDTO.class))
                .collect(Collectors.toList());
    }
}