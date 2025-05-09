package com.example.PaymentGatewayDemo.serviceImpl;

import com.example.PaymentGatewayDemo.dto.ConfirmPaymentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class HmacServiceImpl {

    @Value("${webhook.secret}")
    private String secret;

    public boolean verifySignature(ConfirmPaymentDTO request, String signature) {
        try {
            String payload = request.getPaymentId() + request.getStatus();
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            String computedSignature = Base64.getEncoder().encodeToString(mac.doFinal(payload.getBytes(StandardCharsets.UTF_8)));

            return computedSignature.equals(signature);
        } catch (Exception e) {
            return false;
        }
    }
}
