package com.cybernetics.payment_ms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotEnoughProductException.class)
    public ResponseEntity<Map<String, String>> handleNotEnoughProductException(NotEnoughProductException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(AzericardFailedException.class)
    public ResponseEntity<Map<String, String>> handleAzericardFailedException(AzericardFailedException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("error", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
