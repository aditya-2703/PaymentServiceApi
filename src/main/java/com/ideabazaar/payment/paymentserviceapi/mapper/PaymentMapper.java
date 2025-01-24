package com.ideabazaar.payment.paymentserviceapi.mapper;

import com.ideabazaar.payment.paymentserviceapi.dto.PaymentDTO;
import com.ideabazaar.payment.paymentserviceapi.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    // Map fields from User -> UserDto
    PaymentDTO paymentToPaymentDto(Payment user);

    // Map fields from UserDto -> User
    Payment paymentDtoToPayment(PaymentDTO userDto);
}
