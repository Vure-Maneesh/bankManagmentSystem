package com.BankManagmentSystem.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.BankManagmentSystem.dtos.ManagerTransferRequestDTO;
import com.BankManagmentSystem.dtos.ManagerTransferResponseDTO;
import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchResponseDTO;
import com.BankManagmentSystem.exceptions.BankNotFoundException;
import com.BankManagmentSystem.exceptions.BranchNotFound;
import com.BankManagmentSystem.exceptions.ManagerNotFound;
import com.BankManagmentSystem.interfaces.AdminService;
import com.BankManagmentSystem.model.Branch;
import com.BankManagmentSystem.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // ==============================
    // 1️⃣ GET ALL MANAGERS
    // ==============================
    @GetMapping("/managers")
    public ResponseEntity<List<User>> getAllManagers() {
        return ResponseEntity.ok(adminService.getAllManagers());
    }

    // ==============================
    // 2️⃣ GET PENDING MANAGER REQUESTS
    // ==============================
    // @GetMapping("/manager-requests")
    // public ResponseEntity<List<User>> getPendingManagerRequests() {
    // return ResponseEntity.ok(adminService.getPendingManagers());
    // }

    // ==============================
    // 3️⃣ APPROVE MANAGER
    // ==============================
    @PutMapping("/managers/{managerId}/approve")
    public ResponseEntity<?> approveManager(@PathVariable Long managerId)
            throws ManagerNotFound {

        return ResponseEntity.ok(adminService.approveManager(managerId));
    }

    // ==============================
    // 4️⃣ REJECT MANAGER
    // ==============================
    // @PutMapping("/managers/{managerId}/reject")
    // public ResponseEntity<?> rejectManager(@PathVariable Long managerId)
    // throws ManagerNotFound {

    // return ResponseEntity.ok(adminService.rejectManager(managerId));
    // }

    // ==============================
    // 5️⃣ GET ALL BRANCHES
    // ==============================
    @GetMapping("/branches")
    public ResponseEntity<List<BranchResponseDTO>> getAllBranches() throws BankNotFoundException {
        return ResponseEntity.ok(adminService.getAllBranches());
    }

    // ==============================
    // 6️⃣ GET TOTAL ACCOUNTS PER BRANCH
    // ==============================
    // @GetMapping("/branches/{branchId}/accounts/count")
    // public ResponseEntity<Long> getTotalAccountsInBranch(@PathVariable Long
    // branchId)
    // throws BranchNotFound {

    // return ResponseEntity.ok(adminService.getTotalAccountsInBranch(branchId));
    // }

    // ==============================
    // 7️⃣ TRANSFER MANAGER
    // ==============================
    @PutMapping("/managers/transfer")
    public ResponseEntity<ManagerTransferResponseDTO> transferManager(
            @RequestBody ManagerTransferRequestDTO request)
            throws ManagerNotFound, BranchNotFound {

        return new ResponseEntity<>(adminService.transferManager(request), HttpStatus.ACCEPTED);
    }
}
