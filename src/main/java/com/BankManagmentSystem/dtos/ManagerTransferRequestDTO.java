package com.BankManagmentSystem.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ManagerTransferRequestDTO {
    @NotNull
    private Long managerId;

    @NotNull
    private Long targetBranchId;
}
