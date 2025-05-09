package com.example.PaymentGatewayDemo.controller;

import com.example.PaymentGatewayDemo.dto.ResponseDTO;
import com.example.PaymentGatewayDemo.dto.UserDTO;
import com.example.PaymentGatewayDemo.enums.StatusCodeEnum;
import com.example.PaymentGatewayDemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseDTO register(@Valid @RequestBody UserDTO userDTO, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseDTO(StatusCodeEnum.BAD_REQUEST.getStatusCode(), errors.getAllErrors().get(0).getDefaultMessage(), null);
        }
        return userService.register(userDTO);
    }
}
