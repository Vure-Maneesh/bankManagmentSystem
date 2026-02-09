package com.BankManagmentSystem.dtos;

import com.BankManagmentSystem.model.AccountStatus;
import com.BankManagmentSystem.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {

    private Long accountNumber;
    private String bankName;
    private String branchName;
    private Double balance;
    private AccountStatus status;
    private AccountType accountType;

}
