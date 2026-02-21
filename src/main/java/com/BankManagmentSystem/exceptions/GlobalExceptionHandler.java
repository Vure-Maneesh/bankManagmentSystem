package com.BankManagmentSystem.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle ALL custom exceptions
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handleCustomException(CustomException ex) {

        return ResponseEntity
                .status(ex.getStatus())
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", ex.getStatus().value(),
                        "error", ex.getStatus().getReasonPhrase(),
                        "message", ex.getMessage()));
    }

    // Handle unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {

        return ResponseEntity
                .status(500)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 500,
                        "error", "Internal Server Error",
                        "message", ex.getMessage()));
    }
}