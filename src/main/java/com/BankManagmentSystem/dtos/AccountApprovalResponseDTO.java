package com.BankManagmentSystem.dtos;

import lombok.Data;

@Data
public class AccountApprovalResponseDTO {

    private String message;
    private Long accountNumber;
    private String customerEmail;

}
