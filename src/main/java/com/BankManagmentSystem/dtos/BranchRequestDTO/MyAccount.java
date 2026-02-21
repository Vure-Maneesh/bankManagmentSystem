package com.BankManagmentSystem.dtos.BranchRequestDTO;

import com.BankManagmentSystem.model.AccountType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MyAccount {

    private String name;
    private Long accountNumber;
    private String customerEmail;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @PositiveOrZero(message = "Initial deposit cannot be negative")
    private Double initialDeposit;

    @Size(max = 100, message = "Nominee name should not exceed 100 characters")
    private String nomineeName;

    @Size(max = 50, message = "Nominee relation should not exceed 50 characters")
    private String nomineeRelation;

    private String branchName;

}
