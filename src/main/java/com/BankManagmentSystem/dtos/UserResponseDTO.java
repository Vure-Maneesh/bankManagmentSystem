package com.BankManagmentSystem.dtos;

import com.BankManagmentSystem.model.Role;

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
    private Role role;

}
