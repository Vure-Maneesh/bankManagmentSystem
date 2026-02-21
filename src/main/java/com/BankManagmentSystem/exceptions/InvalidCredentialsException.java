package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends CustomException {

    public InvalidCredentialsException(String msg) {
        super(msg, HttpStatus.UNAUTHORIZED);
    }
}
