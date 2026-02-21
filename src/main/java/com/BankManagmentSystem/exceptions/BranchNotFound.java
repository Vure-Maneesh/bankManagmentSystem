package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class BranchNotFound extends CustomException {

    public BranchNotFound(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
