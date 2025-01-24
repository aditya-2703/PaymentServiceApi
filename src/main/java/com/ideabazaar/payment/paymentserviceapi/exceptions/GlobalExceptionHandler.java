package com.ideabazaar.payment.paymentserviceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PaymentNotFound.class)
    public ResponseEntity<String> handleUserNotFoundException(PaymentNotFound ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Project Not Found" + ex.getMessage());
    }
}
