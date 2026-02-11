package com.BankManagmentSystem.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.dtos.AccountRequestDTO;
import com.BankManagmentSystem.dtos.AccountResponseDTO;
import com.BankManagmentSystem.interfaces.CustomerService;
import com.BankManagmentSystem.model.Account;
import com.BankManagmentSystem.model.AccountStatus;
import com.BankManagmentSystem.model.Branch;
import com.BankManagmentSystem.model.KycStatus;
import com.BankManagmentSystem.model.Role;
import com.BankManagmentSystem.model.User;
import com.BankManagmentSystem.repository.AccountRepository;
import com.BankManagmentSystem.repository.BranchRepository;
import com.BankManagmentSystem.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository repository;

    @Autowired
    ModelMapper mapper;

    @Override
    @Transactional
    public AccountResponseDTO openAccount(AccountRequestDTO accountRequestDTO) {

        User user = new User();

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can request a bank account");
        }

        if (user.getStatus() != KycStatus.APPROVED) {
            throw new RuntimeException("Please complete KYC before requesting an account");
        }

        if (accountRepository.existsByCustomerAndAccountTypeAndStatus(
                user,
                accountRequestDTO.getAccountType(),
                AccountStatus.ACTIVE)) {

            throw new RuntimeException(
                    "You already have an active " + accountRequestDTO.getAccountType() + " account");
        }

        if (accountRepository.existsByCustomerAndAccountTypeAndStatus(
                user,
                accountRequestDTO.getAccountType(),
                AccountStatus.PENDING)) {

            throw new RuntimeException(
                    "You already have a pending request for this account type");
        }

        if (user.getBranch() == null) {

            Branch branch = branchRepository.findById(accountRequestDTO.getBranchId())
                    .orElseThrow(() -> new RuntimeException("Branch not found"));

            user.setBranch(branch);
            repository.save(user);

        } else {

            if (!user.getBranch().getBranchId()
                    .equals(accountRequestDTO.getBranchId())) {

                throw new RuntimeException(
                        "You cannot change branch once assigned");
            }
        }

        Account account = new Account();
        account.setCustomer(user);
        account.setAccountType(accountRequestDTO.getAccountType());
        account.setBalance(
                accountRequestDTO.getInitialDeposit() != null
                        ? accountRequestDTO.getInitialDeposit()
                        : 0.0);
        account.setStatus(AccountStatus.PENDING);

        Account savedAccount = accountRepository.save(account);

        return mapper.map(savedAccount, AccountResponseDTO.class);
    }

}