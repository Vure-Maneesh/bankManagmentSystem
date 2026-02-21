package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class BranchAlreadyExists extends CustomException {
    public BranchAlreadyExists(String msg) {
        super(msg, HttpStatus.CONFLICT);
    }

}
