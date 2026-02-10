package com.BankManagmentSystem.dtos;

import com.BankManagmentSystem.model.KycStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerApprovalResponseDTO {
    private Long managerId;
    private String managerName;
    private KycStatus status;
    private String message;
}
