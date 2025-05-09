package com.example.PaymentGatewayDemo.controller;

import com.example.PaymentGatewayDemo.dto.ConfirmPaymentDTO;
import com.example.PaymentGatewayDemo.dto.PaymentRequestDTO;
import com.example.PaymentGatewayDemo.dto.ResponseDTO;
import com.example.PaymentGatewayDemo.entity.User;
import com.example.PaymentGatewayDemo.enums.StatusCodeEnum;
import com.example.PaymentGatewayDemo.service.PaymentService;
import com.example.PaymentGatewayDemo.serviceImpl.HmacServiceImpl;
import com.example.PaymentGatewayDemo.util.Constants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @Autowired
    HmacServiceImpl hmacService;

    @PostMapping("/initiate")
    public ResponseDTO initiate(@Valid @RequestBody PaymentRequestDTO request, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(), null);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return paymentService.initiate(request, email);
    }

    @PostMapping("/confirm")
    public ResponseDTO confirm(@Valid @RequestBody ConfirmPaymentDTO request, @RequestHeader("X-Signature") String signature, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(), null);
        }
        if (!hmacService.verifySignature(request, signature)) {
            return new ResponseDTO(StatusCodeEnum.FORBIDDEN.getStatusCode(), Constants.INVALID_SIGNATURE, null);
        }

        return paymentService.confirm(request);
    }

    @GetMapping("/{paymentId}")
    public ResponseDTO status(@PathVariable UUID paymentId) {
        return paymentService.status(paymentId);
    }

    @GetMapping
    public ResponseDTO getPayments(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return paymentService.getPayments(email, page, size);
    }
}
