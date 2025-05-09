package com.example.PaymentGatewayDemo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ConfirmPaymentDTO {
    @NotBlank(message = "Payment ID is required")
    private String paymentId;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "SUCCESS|FAILED", message = "Status must be SUCCESS or FAILED")
    private String status;

    public String getPaymentId() {
        return paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ConfirmPaymentDTO(String paymentId, String status) {
        this.paymentId = paymentId;
        this.status = status;
    }

    public ConfirmPaymentDTO(){

    }
}
