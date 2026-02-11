package com.BankManagmentSystem.dtos;

import java.time.LocalDateTime;

import com.BankManagmentSystem.model.AccountStatus;
import com.BankManagmentSystem.model.AccountType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {

    private Long accountId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountNumber; // Only when ACTIVE

    private AccountType accountType;

    private Double balance;

    private AccountStatus status;

    private String branchName;

    private String branchCity;

    private LocalDateTime createdAt;
}
