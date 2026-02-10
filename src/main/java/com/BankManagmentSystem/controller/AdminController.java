package com.BankManagmentSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BankManagmentSystem.dtos.ManagerTransferRequestDTO;
import com.BankManagmentSystem.dtos.ManagerTransferResponseDTO;
import com.BankManagmentSystem.exceptions.BranchNotFound;
import com.BankManagmentSystem.exceptions.ManagerNotFound;
import com.BankManagmentSystem.interfaces.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PutMapping("/managers/{managerId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> approveManager(@PathVariable Long managerId)
            throws ManagerNotFound {

        return ResponseEntity.ok(adminService.approveManager(managerId));

    }

    @PutMapping("/managers/transfer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ManagerTransferResponseDTO> transferManager(
            @RequestBody ManagerTransferRequestDTO request) throws ManagerNotFound, BranchNotFound {

        return new ResponseEntity<>(adminService.transferManager(request), HttpStatus.ACCEPTED);

    }

}
