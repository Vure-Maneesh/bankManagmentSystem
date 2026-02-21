package com.BankManagmentSystem.dtos;

import lombok.Data;

@Data
public class BeneficiaryRequestDTO {

    private String name;
    private String accountNumber;
    private String ifsc;
    private String bankName;
}