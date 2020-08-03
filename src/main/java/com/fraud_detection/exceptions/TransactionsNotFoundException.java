package com.fraud_detection.exceptions;

public class TransactionsNotFoundException extends RuntimeException {
    public TransactionsNotFoundException(String message) {
        super(message);
    }
}
