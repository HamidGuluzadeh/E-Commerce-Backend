package com.cybernetics.payment_ms.exception;

public class NotEnoughProductException extends RuntimeException {
    public NotEnoughProductException(String message) {
        super(message);
    }
}
