package com.example.PaymentGatewayDemo.service;

import com.example.PaymentGatewayDemo.dto.ConfirmPaymentDTO;
import com.example.PaymentGatewayDemo.dto.PaymentRequestDTO;
import com.example.PaymentGatewayDemo.dto.PaymentResponseDTO;
import com.example.PaymentGatewayDemo.dto.ResponseDTO;
import com.example.PaymentGatewayDemo.entity.User;

import java.util.List;
import java.util.UUID;


public interface PaymentService {
    ResponseDTO initiate(PaymentRequestDTO request, String email);

    ResponseDTO confirm(ConfirmPaymentDTO request);

    ResponseDTO status(UUID paymentId);

    ResponseDTO getPayments(String email, int page, int size);
}
