package com.lucasmulato.paymentapi.strategy;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreditCardPaymentStrategy implements PaymentStrategy {

    private static final BigDecimal FEE_RATE = new BigDecimal("0.025");

    @Override
    public PaymentType getType() {
        return PaymentType.CARTAO_CREDITO;
    }

    @Override
    public String processPayment(BigDecimal amount) {
        BigDecimal fee = calculateFee(amount);
        return "Pagamento no cartão autorizado. Valor: R$ " + amount
                + " | Taxa cobrada: R$ " + fee;
    }

    @Override
    public BigDecimal calculateFee(BigDecimal amount) {
        return amount.multiply(FEE_RATE);
    }
}
