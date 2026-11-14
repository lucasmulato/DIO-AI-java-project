package com.lucasmulato.paymentapi.strategy;

import java.math.BigDecimal;

public interface PaymentStrategy {

    PaymentType getType();

    String processPayment(BigDecimal amount);

    default BigDecimal calculateFee(BigDecimal amount) {
        return BigDecimal.ZERO; 
    }
}
