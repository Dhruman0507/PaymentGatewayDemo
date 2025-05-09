package com.example.PaymentGatewayDemo.exception;

public class PaymentsNotFoundException extends RuntimeException{
    public PaymentsNotFoundException(String message) {
        super(message);
    }
}
