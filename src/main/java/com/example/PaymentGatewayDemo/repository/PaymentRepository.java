package com.example.PaymentGatewayDemo.repository;

import com.example.PaymentGatewayDemo.entity.Payment;
import com.example.PaymentGatewayDemo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentId(String paymentId);

    Page<Payment> findAllByUser(User user, Pageable pageable);
}
