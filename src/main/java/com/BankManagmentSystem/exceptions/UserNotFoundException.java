package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException {

    public UserNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
