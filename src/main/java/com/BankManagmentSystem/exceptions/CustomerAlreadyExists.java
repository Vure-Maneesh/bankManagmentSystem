package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerAlreadyExists extends CustomException {

    public CustomerAlreadyExists(String msg) {
        super(msg, HttpStatus.CONFLICT);
    }

}
