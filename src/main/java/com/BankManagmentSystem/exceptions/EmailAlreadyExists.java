package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExists extends CustomException {
    public EmailAlreadyExists(String msg) {
        super(msg, HttpStatus.CONFLICT);
    }

}
