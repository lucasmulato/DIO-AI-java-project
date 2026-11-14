package com.lucasmulato.paymentapi.controller;

import com.lucasmulato.paymentapi.facade.PaymentFacade;
import com.lucasmulato.paymentapi.model.Payment;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    public PaymentController(PaymentFacade paymentFacade) {
        this.paymentFacade = paymentFacade;
    }

    @PostMapping
    public ResponseEntity<Payment> pay(@Valid @RequestBody PaymentRequest request) {
        Payment payment = paymentFacade.processPayment(request.type(), request.amount());
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }

    @GetMapping
    public List<Payment> listAll() {
        return paymentFacade.listAll();
    }
}
