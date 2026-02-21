package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class BankAlreadyExits extends CustomException {
    public BankAlreadyExits(String msg) {
        super(msg, HttpStatus.CONFLICT);
    }

}
