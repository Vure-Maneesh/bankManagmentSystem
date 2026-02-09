package com.BankManagmentSystem.dtos.BankRequestDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankResponseDTO {

    private Long id;
    private String bankName;
    private String ifscCode;
    private LocalDateTime createdLocalDateTime;

    private LocalDateTime updatedLocalDateTime;

}
