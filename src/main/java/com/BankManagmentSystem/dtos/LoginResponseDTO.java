package com.BankManagmentSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private String token;
    private Long userId;
    private String role;

    // optional (useful for manager/customer)
    private Long branchId;

}
