package com.BankManagmentSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.BankManagmentSystem.dtos.ManagerTransferRequestDTO;
import com.BankManagmentSystem.dtos.ManagerTransferResponseDTO;
import com.BankManagmentSystem.dtos.RegistrationResponseDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchResponseDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchRequestDTO;
import com.BankManagmentSystem.dtos.BankRequestDTO.BankRequestDTO;
import com.BankManagmentSystem.dtos.BankRequestDTO.BankResponseDTO;
import com.BankManagmentSystem.exceptions.BankNotFoundException;
import com.BankManagmentSystem.exceptions.BankAlreadyExits;
import com.BankManagmentSystem.exceptions.BranchNotFound;
import com.BankManagmentSystem.exceptions.BranchAlreadyExists;
import com.BankManagmentSystem.exceptions.ManagerNotFound;
import com.BankManagmentSystem.interfaces.AdminService;
import com.BankManagmentSystem.interfaces.AuthService;
import com.BankManagmentSystem.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Autowired
    AuthService authService;

    @GetMapping("/managers")
    public ResponseEntity<List<RegistrationResponseDTO>> getAllManagers() {
        return ResponseEntity.ok(adminService.getAllManagers());
    }

    @PutMapping("/managers/{managerId}/approve")
    public ResponseEntity<?> approveManager(@PathVariable Long managerId)
            throws ManagerNotFound {

        return ResponseEntity.ok(adminService.approveManager(managerId));
    }

    // @PutMapping("/managers/{managerId}/reject")
    // public ResponseEntity<?> rejectManager(@PathVariable Long managerId)
    // throws ManagerNotFound {

    // return ResponseEntity.ok(adminService.rejectManager(managerId));
    // }

    @PostMapping("/add/bank")
    public ResponseEntity<BankResponseDTO> createBank(@RequestBody BankRequestDTO dto)
            throws BankAlreadyExits {

        return new ResponseEntity<>(adminService.createBank(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getBank")
    public ResponseEntity<BankResponseDTO> getBank() {
        return ResponseEntity.ok(adminService.getBank());
    }

    @PostMapping("/add/branch")
    public ResponseEntity<BranchResponseDTO> createBranch(@RequestBody BranchRequestDTO dto)
            throws BankNotFoundException, BranchAlreadyExists {

        return new ResponseEntity<>(adminService.createBranch(dto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll/branches")
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() throws BankNotFoundException {
        return ResponseEntity.ok(adminService.getAllBranches());
    }

    // @GetMapping("/branches/{branchId}/accounts/count")
    // public ResponseEntity<Long> getTotalAccountsInBranch(@PathVariable Long
    // branchId)
    // throws BranchNotFound {

    // return ResponseEntity.ok(adminService.getTotalAccountsInBranch(branchId));
    // }

    @PutMapping("/managers/transfer")
    public ResponseEntity<ManagerTransferResponseDTO> transferManager(
            @RequestBody ManagerTransferRequestDTO request)
            throws ManagerNotFound, BranchNotFound {

        return new ResponseEntity<>(adminService.transferManager(request), HttpStatus.ACCEPTED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<RegistrationResponseDTO>> getUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }
}
