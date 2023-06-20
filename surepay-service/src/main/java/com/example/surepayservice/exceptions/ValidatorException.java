package com.example.surepayservice.exceptions;

public class ValidatorException extends RuntimeException {
    public ValidatorException(String message, Exception cause) {
        super(message, cause);
    }

    public ValidatorException(String message) {
        super(message);
    }
}

