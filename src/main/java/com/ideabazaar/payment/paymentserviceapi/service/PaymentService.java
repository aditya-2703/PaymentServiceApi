package com.ideabazaar.payment.paymentserviceapi.service;

import com.ideabazaar.payment.paymentserviceapi.dto.PaymentDTO;
import com.ideabazaar.payment.paymentserviceapi.exceptions.PaymentNotFound;
import com.ideabazaar.payment.paymentserviceapi.mapper.PaymentMapper;
import com.ideabazaar.payment.paymentserviceapi.model.Payment;
import com.ideabazaar.payment.paymentserviceapi.repository.PaymentRepository;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    PaymentRepository paymentRepository;
    RazorpayClient razorpayClient;
    public PaymentService(PaymentRepository PaymentRepository,RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
        this.paymentRepository = PaymentRepository;
    }

    // C
    public PaymentDTO createPayment(PaymentDTO PaymentDTO) {
        // dto -> Payment
        Payment Payment = PaymentMapper.INSTANCE.paymentDtoToPayment(PaymentDTO);
        // save to db
        Payment updatedPayment = paymentRepository.save(Payment);
        // Payment -> dto
        return PaymentMapper.INSTANCE.paymentToPaymentDto(updatedPayment);
    }

    // R
    public PaymentDTO getSinglePayment(Long id) {
        Optional<Payment> Payment = this.paymentRepository.findById(id);
        // Payment -> dto
        return PaymentMapper.INSTANCE.paymentToPaymentDto(Payment.get());
    }
    public List<PaymentDTO> getAllPayment() {
        List<Payment> Payments = this.paymentRepository.findAll();
        // Payment -> dto
        List<PaymentDTO> PaymentDTOs = new ArrayList<>();
        for (Payment Payment : Payments) {
            PaymentDTOs.add(PaymentMapper.INSTANCE.paymentToPaymentDto(Payment));
        }
        return PaymentDTOs;
    }

    // U
    public PaymentDTO updatePayment(PaymentDTO PaymentDTO) {
        // dto -> user
        Payment user = PaymentMapper.INSTANCE.paymentDtoToPayment(PaymentDTO);
        if(user!=null){
            Payment updateduser = this.paymentRepository.save(user);
            // user -> dto
            return PaymentMapper.INSTANCE.paymentToPaymentDto(updateduser);
        }else{
            return null;
        }
    }


    // D
    public boolean deletePayment(Long id) throws PaymentNotFound {
        if (paymentRepository.existsById(id)) {
            paymentRepository.deleteById(id);
            return true;
        } else {
            throw new PaymentNotFound(id);
        }
    }


    public String getPaymentLink(String orderid) throws RazorpayException {

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",true);
        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by", Calendar.getInstance().getTimeInMillis() + 15*60*1000);
        paymentLinkRequest.put("reference_id",orderid);
        paymentLinkRequest.put("description","Payment for policy no #23456");

        JSONObject customer = new JSONObject();
        customer.put("name","+919999999999");
        customer.put("contact","Gaurav Kumar");
        customer.put("email","gaurav.kumar@example.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Jeevan Bima");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://google.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.toString();
    }
}
