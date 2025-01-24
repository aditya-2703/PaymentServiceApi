package com.ideabazaar.payment.paymentserviceapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class PaymentNotFound extends Exception{

    public PaymentNotFound(Long id){
        super("Project is Not present in Database with id ="+id);
    }
}
