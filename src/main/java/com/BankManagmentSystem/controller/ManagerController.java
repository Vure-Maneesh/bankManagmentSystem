package com.BankManagmentSystem.controller;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BankManagmentSystem.exceptions.AccountException;
import com.BankManagmentSystem.exceptions.ManagerNotFound;
import com.BankManagmentSystem.interfaces.ManagerService;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PutMapping("/approve/{accountId}")
    public ResponseEntity<String> approveAccount(
            @PathVariable Long accountId) throws AccountNotFoundException, ManagerNotFound, AccountException {

        managerService.approveAccount(accountId);

        return ResponseEntity.ok("Account approved successfully");
    }

    @PutMapping("/reject/{accountId}")
    public ResponseEntity<String> rejectAccount(
            @PathVariable Long accountId) {

        managerService.rejectAccount(accountId);

        return ResponseEntity.ok("Account rejected successfully");
    }

    @GetMapping("/allcustomers")
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(managerService.getAllAccounts(), HttpStatus.OK);
    }
}
