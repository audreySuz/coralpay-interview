package com.example.dynamobankservice.exceptions;


public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String message, Exception cause) {
        super(message, cause);
    }
    public AuthenticationException(String message) {
        super(message);
    }
}