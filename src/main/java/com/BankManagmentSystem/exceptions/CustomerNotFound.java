package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerNotFound extends CustomException {
    public CustomerNotFound(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
