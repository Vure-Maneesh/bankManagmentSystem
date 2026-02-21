package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class AdminAlreadyExists extends CustomException {
    public AdminAlreadyExists(String msg) {
        super(msg, HttpStatus.CONFLICT);
    }

}
