package com.example.PaymentGatewayDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class PaymentResponseDTO {
    private String paymentId;
    private String paymentLink;

    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public PaymentResponseDTO(String paymentId, String paymentLink) {
        this.paymentId = paymentId;
        this.paymentLink = paymentLink;
    }

    public PaymentResponseDTO(){

    }
}
