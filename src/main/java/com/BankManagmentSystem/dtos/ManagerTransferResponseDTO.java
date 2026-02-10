package com.BankManagmentSystem.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerTransferResponseDTO {
    private Long managerId;
    private Long fromBranchId;
    private Long toBranchId;
    private String message;
}
