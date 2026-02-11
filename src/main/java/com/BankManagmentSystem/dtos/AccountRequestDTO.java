package com.BankManagmentSystem.dtos;

import com.BankManagmentSystem.model.AccountType;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequestDTO {

    @NotNull(message = "Branch selection is required")
    private Long branchId;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @PositiveOrZero(message = "Initial deposit cannot be negative")
    private Double initialDeposit;

    @Size(max = 100, message = "Nominee name should not exceed 100 characters")
    private String nomineeName;

    @Size(max = 50, message = "Nominee relation should not exceed 50 characters")
    private String nomineeRelation;
}
