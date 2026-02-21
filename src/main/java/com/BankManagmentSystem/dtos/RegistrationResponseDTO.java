package com.BankManagmentSystem.dtos;

import com.BankManagmentSystem.model.KycStatus;
import com.BankManagmentSystem.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private String mobile;
    private Role role;
    private KycStatus status;

    private Long branchId;
    private String branchName;

    private Long bankId;
    private String bankName;
    private String message;
    private String token;
}
