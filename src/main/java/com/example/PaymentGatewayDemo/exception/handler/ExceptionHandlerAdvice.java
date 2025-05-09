package com.example.PaymentGatewayDemo.exception.handler;

import com.example.PaymentGatewayDemo.dto.ResponseDTO;
import com.example.PaymentGatewayDemo.exception.InvalidPaymentIdException;
import com.example.PaymentGatewayDemo.exception.PaymentsNotFoundException;
import com.example.PaymentGatewayDemo.exception.UserAlreadyExistException;
import com.example.PaymentGatewayDemo.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO userAlreadyExist(UserAlreadyExistException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO userNotFound(UserNotFoundException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(InvalidPaymentIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDTO userNotFound(InvalidPaymentIdException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.BAD_REQUEST.value()), exception.getMessage(), null);
    }

    @ExceptionHandler(PaymentsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseDTO userNotFound(PaymentsNotFoundException exception) {
        return new ResponseDTO(String.valueOf(HttpStatus.NOT_FOUND.value()), exception.getMessage(), null);
    }
}
