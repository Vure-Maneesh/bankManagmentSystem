package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class MobileNumberAlreadyExists extends CustomException {
    public MobileNumberAlreadyExists(String msg) {
        super(msg, HttpStatus.CONFLICT);
    }

}
