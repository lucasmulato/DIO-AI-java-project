package com.lucasmulato.paymentapi.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BoletoPaymentStrategy implements PaymentStrategy {

    private static final BigDecimal FIXED_FEE = new BigDecimal("3.50");

    @Override
    public PaymentType getType() {
        return PaymentType.BOLETO;
    }

    @Override
    public String processPayment(BigDecimal amount) {
        BigDecimal fee = calculateFee(amount);
        return "Boleto gerado. Valor: R$ " + amount
                + " | Taxa fixa: R$ " + fee
                + ". Compensação em D+1.";
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return FIXED_FEE;
    }
}
