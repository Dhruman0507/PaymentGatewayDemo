package com.example.PaymentGatewayDemo.exception;

public class InvalidPaymentIdException extends RuntimeException{
    public InvalidPaymentIdException(String message) {
        super(message);
    }
}
