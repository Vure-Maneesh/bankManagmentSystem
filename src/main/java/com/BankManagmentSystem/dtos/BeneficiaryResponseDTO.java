package com.BankManagmentSystem.dtos;

import lombok.Data;

@Data
public class BeneficiaryResponseDTO {

    private Long id;
    private String name;
    private String accountNumber;
    private String ifsc;
    private String bankName;
}