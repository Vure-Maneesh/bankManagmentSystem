package com.BankManagmentSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long userId;
    private String name;
    private String email;
    private String role;
    private String branchName; // instead of full Branch object
}
