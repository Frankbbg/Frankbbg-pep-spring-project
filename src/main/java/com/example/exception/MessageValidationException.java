package com.example.exception;

public class MessageValidationException extends RuntimeException {
    public MessageValidationException(String message) {
        super(message);
    }
}