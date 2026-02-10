package com.BankManagmentSystem.dtos;

import com.BankManagmentSystem.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String token;
    private Long userId;
    private Role role;
    private Long branchId;

}
