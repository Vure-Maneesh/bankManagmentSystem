package com.BankManagmentSystem.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagerRegister {

    @NotBlank
    private String fullName;

    @NotNull
    @Past
    private LocalDate dob;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^[6-9]\\d{9}$")
    private String mobile;

    @NotBlank
    @Size(min = 8, max = 20)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotNull
    private Long branchId;
}
