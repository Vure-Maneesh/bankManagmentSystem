package com.BankManagmentSystem.dtos.BankRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankRequestDTO {

    private String bankName;
    private String ifscCode;
    private String headOffice;

}
