package com.example.PaymentGatewayDemo.dto;

public class PaymentDTO {
    private String payment_id;
    private Double amount;
    private String currency;
    private String status;
    private String description;
    private Long user_id;

    public PaymentDTO(String payment_id, Double amount, String currency, String status, String description, Long user_id) {
        this.payment_id = payment_id;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.description = description;
        this.user_id = user_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
