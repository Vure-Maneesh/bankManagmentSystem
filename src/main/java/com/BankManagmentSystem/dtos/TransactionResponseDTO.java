package com.BankManagmentSystem.dtos;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionResponseDTO {

    private String transactionId;
    private String type; // CREDIT / DEBIT
    private Double amount;
    private String description;
    private String method;
    private Double balanceAfter;
    private LocalDateTime transactionDate;
}
