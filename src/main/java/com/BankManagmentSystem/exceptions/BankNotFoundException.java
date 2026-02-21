package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class BankNotFoundException extends CustomException {

    public BankNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
