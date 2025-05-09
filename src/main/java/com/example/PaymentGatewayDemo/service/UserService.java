package com.example.PaymentGatewayDemo.service;

import com.example.PaymentGatewayDemo.dto.ResponseDTO;
import com.example.PaymentGatewayDemo.dto.UserDTO;

public interface UserService {
    ResponseDTO register(UserDTO userDTO);
}
