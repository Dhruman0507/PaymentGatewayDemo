package com.example.PaymentGatewayDemo.enums;

public enum StatusCodeEnum {

    OK("200"),
    CREATED("201"),
    NOT_FOUND("404"),
    BAD_REQUEST("400"),
    FORBIDDEN("403");
    
    StatusCodeEnum(String statusCode) {
        this.statusCode = statusCode;
    }

    private String statusCode;

    public String getStatusCode() {
        return statusCode;
    }
}
