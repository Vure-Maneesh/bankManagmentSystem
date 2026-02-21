package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class AccountException extends CustomException {
    public AccountException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

}
