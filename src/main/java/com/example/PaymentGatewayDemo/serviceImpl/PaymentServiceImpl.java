package com.example.PaymentGatewayDemo.serviceImpl;

import com.example.PaymentGatewayDemo.dto.*;
import com.example.PaymentGatewayDemo.entity.Payment;
import com.example.PaymentGatewayDemo.entity.User;
import com.example.PaymentGatewayDemo.enums.PaymentStatusEnum;
import com.example.PaymentGatewayDemo.enums.StatusCodeEnum;
import com.example.PaymentGatewayDemo.exception.InvalidPaymentIdException;
import com.example.PaymentGatewayDemo.exception.PaymentsNotFoundException;
import com.example.PaymentGatewayDemo.exception.UserNotFoundException;
import com.example.PaymentGatewayDemo.repository.PaymentRepository;
import com.example.PaymentGatewayDemo.repository.UserRepository;
import com.example.PaymentGatewayDemo.service.PaymentService;
import com.example.PaymentGatewayDemo.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseDTO initiate(PaymentRequestDTO request, String email) {
        User user = userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND));
        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setDescription(request.getDescription());
        payment.setStatus(PaymentStatusEnum.PENDING);
        payment.setUser(user);

        Payment saved = paymentRepository.save(payment);

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(saved.getPaymentId());
        response.setPaymentLink(Constants.MOCK_PAYMENT_LINK + saved.getPaymentId());
        return new ResponseDTO(StatusCodeEnum.CREATED.getStatusCode(), Constants.PAYMENT_INITIATED_SUCCESSFULLY, response);
    }

    @Override
    public ResponseDTO confirm(ConfirmPaymentDTO request) {
        Optional<Payment> payment = paymentRepository.findByPaymentId(request.getPaymentId());
        if (payment.isEmpty()) {
            throw new InvalidPaymentIdException(Constants.INVALID_PAYMENT_ID);
        }
        if (payment.get().getStatus() == PaymentStatusEnum.SUCCESS || payment.get().getStatus() == PaymentStatusEnum.FAILED) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), Constants.PAYMENT_ALREADY_CONFIRMED_AS + payment.get().getStatus(), null);
        }
        PaymentStatusEnum newStatus = PaymentStatusEnum.valueOf(request.getStatus());
        payment.get().setStatus(newStatus);
        paymentRepository.save(payment.get());
        return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.PAYMENT_STATUS_UPDATED_TO + newStatus, null);
    }

    @Override
    public ResponseDTO status(UUID paymentId) {
        Payment payment = paymentRepository.findByPaymentId(String.valueOf(paymentId)).orElseThrow(() -> new InvalidPaymentIdException(Constants.INVALID_PAYMENT_ID));
        Long userId = payment.getUser().getId();
        PaymentDTO paymentDTO = new PaymentDTO(payment.getPaymentId(),
                payment.getAmount(),
                payment.getCurrency(),
                payment.getStatus().name(),
                payment.getDescription(),
                userId);
        return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.PAYMENT_FETCHED_SUCCESSFULLY, paymentDTO);
    }

    @Override
    public ResponseDTO getPayments(String email, int page, int size) {
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException(Constants.USER_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Payment> payments = paymentRepository.findAllByUser(user, pageable);
        if (payments.isEmpty()) {
            throw new PaymentsNotFoundException(Constants.PAYMENTS_NOT_FOUND);
        }
        return new ResponseDTO(StatusCodeEnum.OK.getStatusCode(), Constants.PAYMENT_FETCHED_SUCCESSFULLY, payments);
    }
}
