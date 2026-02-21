package com.BankManagmentSystem.exceptions;

import org.springframework.http.HttpStatus;

public class ManagerNotFound extends CustomException {
    public ManagerNotFound(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }

}
