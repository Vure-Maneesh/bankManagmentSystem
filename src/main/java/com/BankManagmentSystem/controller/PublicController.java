package com.BankManagmentSystem.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BankManagmentSystem.dtos.BranchRequestDTO.BranchResponseDTO;
import com.BankManagmentSystem.exceptions.BankNotFoundException;
import com.BankManagmentSystem.interfaces.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PublicController {

    private final AdminService adminService;

    @GetMapping("/branches")
    public ResponseEntity<List<BranchResponseDTO>> getBranches() throws BankNotFoundException {
        return ResponseEntity.ok(adminService.getAllBranches());
    }
}
