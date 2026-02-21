package com.BankManagmentSystem.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.BankManagmentSystem.dtos.AccountRequestDTO;
import com.BankManagmentSystem.interfaces.CustomerService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    public CustomerService customerService;

    @PostMapping("/accounts/request")
    public ResponseEntity<?> openAccount(@RequestBody AccountRequestDTO accountRequest) {
        return new ResponseEntity<>(customerService.openAccount(accountRequest), HttpStatus.CREATED);
    }

    // @GetMapping("/statement")
    // public ResponseEntity<List<TransactionResponseDTO>> getStatement(
    // @RequestParam String from,
    // @RequestParam String to,
    // Principal principal) {

    // return ResponseEntity.ok(
    // transactionService.getStatement(principal.getName(), from, to));
    // }

}
