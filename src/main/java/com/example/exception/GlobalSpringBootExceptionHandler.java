package com.example.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalSpringBootExceptionHandler {
    /***
     * Handles the case where an authentication error occurs in the Spring Boot application.
     * @param e
     * @return a ResponseEntity of status 401 (Unauthorized) and an error message
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleAuthenticationError(InvalidCredentialsException e) {
        return ResponseEntity.status(401).body(e.getMessage());
    }

    /***
     * Handles the case where a conflict error occurs in the Spring Boot application.
     * @param e
     * @return a ResponseEntity of status 409 (Conflict) and an error message
     */
    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleConflictError(DuplicateUsernameException e) {
        return ResponseEntity.status(409).body(e.getMessage());
    }

    /***
     * Handles the case where a bad request error occurs in the Spring Boot application
     * @param e
     * @return a ResponseEntity of status 400 (Bad Request) and an error message
     */
    @ExceptionHandler({MessageNotFoundException.class, UserNotFoundException.class, MessageValidationException.class})
    public ResponseEntity<String> handleBadRequest(RuntimeException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }
}
