package com.BankManagmentSystem.dtos.BranchRequestDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchResponseDTO {

    private Long id;
    private String name;
    private String city;
    private String address;
    private String bankName;
    private LocalDateTime createdLocalDateTime;

    private LocalDateTime updatedLocalDateTime;

}
