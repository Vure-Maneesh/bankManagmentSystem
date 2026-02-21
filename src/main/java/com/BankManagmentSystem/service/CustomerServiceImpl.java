package com.BankManagmentSystem.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.BankManagmentSystem.dtos.AccountRequestDTO;
import com.BankManagmentSystem.dtos.AccountResponseDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.MyAccount;
import com.BankManagmentSystem.exceptions.CustomException;
import com.BankManagmentSystem.exceptions.CustomerNotFound;
import com.BankManagmentSystem.interfaces.CustomerService;
import com.BankManagmentSystem.model.*;
import com.BankManagmentSystem.repository.*;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public AccountResponseDTO openAccount(AccountRequestDTO dto) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.CUSTOMER) {
            throw new RuntimeException("Only customers can request accounts");
        }

        if (user.getStatus() != KycStatus.APPROVED) {
            throw new RuntimeException("Please complete KYC before requesting account");
        }

        // Prevent duplicate ACTIVE account
        if (accountRepository.existsByCustomerAndAccountTypeAndStatus(
                user, dto.getAccountType(), AccountStatus.ACTIVE)) {

            throw new RuntimeException(
                    "You already have an active " + dto.getAccountType() + " account");
        }

        // Prevent duplicate PENDING request
        if (accountRepository.existsByCustomerAndAccountTypeAndStatus(
                user, dto.getAccountType(), AccountStatus.PENDING)) {

            throw new RuntimeException(
                    "You already have a pending request for this account type");
        }

        Branch branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new RuntimeException("Branch not found"));

        if (user.getBranch() == null) {
            user.setBranch(branch);
            userRepository.save(user);
        } else if (!user.getBranch().getBranchId().equals(dto.getBranchId())) {
            throw new RuntimeException("You cannot change branch once assigned");
        }

        Account account = new Account();
        account.setCustomer(user);
        account.setAccountType(dto.getAccountType());
        account.setBalance(
                dto.getInitialDeposit() != null ? dto.getInitialDeposit() : 0.0);
        account.setStatus(AccountStatus.PENDING);

        Account saved = accountRepository.save(account);

        return mapper.map(saved, AccountResponseDTO.class);
    }

}