package com.ideabazaar.payment.paymentserviceapi.controller;

import com.ideabazaar.payment.paymentserviceapi.dto.PaymentDTO;
import com.ideabazaar.payment.paymentserviceapi.exceptions.PaymentNotFound;
import com.ideabazaar.payment.paymentserviceapi.service.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/payment",produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {

    PaymentService paymentService;
    PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // inbuild-integration
    @PostMapping("/payment-link/{orderid}")
    public String getPaymentLink(@PathVariable String orderid) throws RazorpayException {
        return this.paymentService.getPaymentLink(orderid);
    }

    // C - Payment
    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO PaymentDTO) {
        PaymentDTO Paymentdto = this.paymentService.createPayment(PaymentDTO);
        return new ResponseEntity<>(Paymentdto, HttpStatus.CREATED);
    }

    // R - all
    @GetMapping("/payments")
    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
        List<PaymentDTO> Paymentsdto = this.paymentService.getAllPayment();
        return new ResponseEntity<>(Paymentsdto, HttpStatus.OK);
    }
    // R - single
    @GetMapping("/payment/{id}")
    public ResponseEntity<PaymentDTO> getSinglePayment(@PathVariable Long id) throws PaymentNotFound {
        PaymentDTO Paymentdto = this.paymentService.getSinglePayment(id);
        if(Paymentdto == null) {
            throw new PaymentNotFound(id); // Throw the exception
        }
        return new ResponseEntity<>(Paymentdto, HttpStatus.OK);
    }

    // U - update
    @PutMapping("/payment/")
    public ResponseEntity<PaymentDTO> updatePayment(@RequestBody PaymentDTO PaymentDTO) throws PaymentNotFound {
        PaymentDTO updatedPaymentdto = this.paymentService.updatePayment(PaymentDTO);
        if(updatedPaymentdto == null) {
            throw new PaymentNotFound(PaymentDTO.getId());
        }
        return new ResponseEntity<>(updatedPaymentdto, HttpStatus.OK);
    }

    // D - Delete
    @DeleteMapping("/payment/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable Long id) throws PaymentNotFound {
        boolean isDeleted = this.paymentService.deletePayment(id);

        if (isDeleted) {
            return ResponseEntity.ok("Payment  with ID " + id + " has been deleted."); // 200 OK with message
        } else {
            throw new PaymentNotFound(id);
        }
    }

}
